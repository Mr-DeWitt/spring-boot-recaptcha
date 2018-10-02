package com.szityu.oss.spring.recaptcha.lib.callbackhandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This interface defines how a filter callback handler class should look like.
 *
 * @author Szilard Laszlo Fodor
 */
public interface ServletRequestFilterCallbackHandler {
    void handle(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException;
}
