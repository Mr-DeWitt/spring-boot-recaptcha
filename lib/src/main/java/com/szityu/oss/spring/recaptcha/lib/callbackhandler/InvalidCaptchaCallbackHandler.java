package com.szityu.oss.spring.recaptcha.lib.callbackhandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Callback handler which will be invoked in case of invalid reCAPTCHA value.
 *
 * @author Szilard Laszlo Fodor
 */
public class InvalidCaptchaCallbackHandler implements ServletRequestFilterCallbackHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        response.setStatus(BAD_REQUEST.value());
        response.getWriter().append("INVALID_CAPTCHA");

    }
}
