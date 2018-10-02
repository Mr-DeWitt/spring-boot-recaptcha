package com.szityu.oss.spring.recaptcha.autoconfigure;

import com.szityu.oss.spring.recaptcha.lib.callbackhandler.ValidCaptchaCallbackHandler;
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
