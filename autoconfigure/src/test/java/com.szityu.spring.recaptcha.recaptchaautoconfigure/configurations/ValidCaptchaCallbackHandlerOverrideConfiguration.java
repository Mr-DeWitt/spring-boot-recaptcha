package com.szityu.spring.recaptcha.recaptchaautoconfigure.configurations;

import com.szityu.spring.recaptcha.lib.callbackhandler.ValidCaptchaCallbackHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Szilard Laszlo Fodor
 */
@Configuration
public class ValidCaptchaCallbackHandlerOverrideConfiguration {
    @Bean
    public OwnValidCaptchaCallbackHandler validCaptchaCallbackHandler() {
        return new OwnValidCaptchaCallbackHandler();
    }

    public static class OwnValidCaptchaCallbackHandler extends ValidCaptchaCallbackHandler {

    }
}
