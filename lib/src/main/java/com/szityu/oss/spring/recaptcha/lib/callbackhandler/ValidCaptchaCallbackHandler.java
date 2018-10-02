package com.szityu.oss.spring.recaptcha.lib.callbackhandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Callback handler which will be invoked in case of valid reCAPTCHA value.
 *
 * @author Szilard Laszlo Fodor
 */
public class ValidCaptchaCallbackHandler implements ServletRequestFilterCallbackHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(request, response);
    }
}
