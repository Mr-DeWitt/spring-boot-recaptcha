package com.szityu.oss.spring.recaptcha.autoconfigure;

import com.szityu.oss.spring.recaptcha.lib.callbackhandler.InvalidCaptchaCallbackHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Szilard Laszlo Fodor
 */
@Configuration
public class InvalidCaptchaCallbackHandlerOverrideConfiguration {
    @Bean
    public OwnInvalidCaptchaCallbackHandler invalidCaptchaCallbackHandler() {
        return new OwnInvalidCaptchaCallbackHandler();
    }

    public static class OwnInvalidCaptchaCallbackHandler extends InvalidCaptchaCallbackHandler {

    }
}
