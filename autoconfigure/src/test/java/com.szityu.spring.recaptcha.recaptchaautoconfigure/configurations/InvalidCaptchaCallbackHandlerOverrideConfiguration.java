package com.szityu.spring.recaptcha.recaptchaautoconfigure.configurations;

import com.szityu.spring.recaptcha.lib.callbackhandler.InvalidCaptchaCallbackHandler;
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
