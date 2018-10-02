package com.szityu.oss.spring.recaptcha.lib;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * This filter checks whether a user sent a valid reCAPTCHA value in the specified HTTP header field.
 *
 * @author Szilard Laszlo Fodor
 */
@Slf4j
@Getter(PROTECTED)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RecaptchaValidatorFilter extends OncePerRequestFilter {

    String reCaptchaSecret;
    String reCaptchaHeaderName;
    List<RequestMatcher> requestMatchers;

    public RecaptchaValidatorFilter(RecaptchaConfigProperties reCaptchaConfigProperties) {
        if (reCaptchaConfigProperties.getSecret() == null)
            throw new IllegalArgumentException("A reCAPTCHTA secret must be set");

        this.reCaptchaSecret = reCaptchaConfigProperties.getSecret();
        this.reCaptchaHeaderName = reCaptchaConfigProperties.getHeaderName();
        this.requestMatchers = stream(ofNullable(reCaptchaConfigProperties.getProtectedUrls()).orElseGet(() -> new String[0]))
                .map(AntPathRequestMatcher::new)
                .collect(toList());

        doDebugLog(reCaptchaConfigProperties.getProtectedUrls());
    }

    protected void doDebugLog(String[] protectedUrls) {
        if (protectedUrls == null) {
            log.warn("There has been no CAPTCHA protected URL set");
        } else {
            log.debug("CAPTCHA protected URLs have been registered: " + Arrays.toString(protectedUrls));
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return requestMatchers.stream()
                .noneMatch(matcher -> matcher.matches(request));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (!validateRequest(request, response, filterChain)) return;

        val reCaptchaResponse = validateCaptcha(request);

        if (reCaptchaResponse.isSuccess())
            handleValidCaptcha(request, response, filterChain);
        else
            handleInvalidCaptcha(request, response, filterChain);
    }

    private boolean validateRequest(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (request.getHeader(reCaptchaHeaderName) == null) {
            handleMissingCaptcha(request, response, filterChain);
            return false;
        }
        return true;
    }

    protected void handleMissingCaptcha(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        response.setStatus(BAD_REQUEST.value());
        response.getWriter().append("MISSING_CAPTCHA");
    }

    protected RecaptchaResponse validateCaptcha(HttpServletRequest request) {
        return RecaptchaValidator.builder()
                .secret(reCaptchaSecret)
                .userCaptchaResponse(request.getHeader(reCaptchaHeaderName))
                .remoteIp(request.getRemoteHost())
                .build()
                .validate();
    }

    protected void handleValidCaptcha(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(request, response);
    }

    protected void handleInvalidCaptcha(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        response.setStatus(BAD_REQUEST.value());
        response.getWriter().append("INVALID_CAPTCHA");
    }
}

