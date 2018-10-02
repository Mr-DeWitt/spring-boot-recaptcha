package com.szityu.spring.recaptcha.lib;

import com.szityu.spring.recaptcha.lib.callbackhandler.InvalidCaptchaCallbackHandler;
import com.szityu.spring.recaptcha.lib.callbackhandler.MissingCaptchaCallbackHandler;
import com.szityu.spring.recaptcha.lib.callbackhandler.ValidCaptchaCallbackHandler;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

/**
 * This filter checks whether a user sent a valid reCAPTCHA value in the specified HTTP header field.
 * It extends it's parent's behaviour by callback functionality. We can initialize it with callback classes for
 * missing / valid / invalid reCAPTCHA value scenarios.
 *
 * @author Szilard Laszlo Fodor
 */
@Slf4j
@Getter(PROTECTED)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RecaptchaValidatorCallbackFilter extends RecaptchaValidatorFilter {

    MissingCaptchaCallbackHandler missingCaptchaCallbackHandler;
    ValidCaptchaCallbackHandler validCaptchaCallbackHandler;
    InvalidCaptchaCallbackHandler invalidCaptchaCallbackHandler;

    public RecaptchaValidatorCallbackFilter(
            RecaptchaConfigProperties reCaptchaConfigProperties,
            MissingCaptchaCallbackHandler missingCaptchaCallbackHandler,
            ValidCaptchaCallbackHandler validCaptchaCallbackHandler,
            InvalidCaptchaCallbackHandler invalidCaptchaCallbackHandler) {
        super(reCaptchaConfigProperties);
        this.missingCaptchaCallbackHandler = missingCaptchaCallbackHandler;
        this.validCaptchaCallbackHandler = validCaptchaCallbackHandler;
        this.invalidCaptchaCallbackHandler = invalidCaptchaCallbackHandler;
    }


    @Override
    protected void handleMissingCaptcha(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        missingCaptchaCallbackHandler.handle(request, response, filterChain);
    }

    @Override
    protected void handleValidCaptcha(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        validCaptchaCallbackHandler.handle(request, response, filterChain);
    }

    @Override
    protected void handleInvalidCaptcha(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        invalidCaptchaCallbackHandler.handle(request, response, filterChain);
    }
}

