package com.szityu.spring.recaptcha.recaptchaautoconfigure.configurations;

import com.szityu.spring.recaptcha.lib.callbackhandler.MissingCaptchaCallbackHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Szilard Laszlo Fodor
 */
@Configuration
public class MissingCaptchaCallbackHandlerOverrideConfiguration {
    @Bean
    public OwnMissingCaptchaCallbackHandler missingCaptchaCallbackHandler() {
        return new OwnMissingCaptchaCallbackHandler();
    }

    public static class OwnMissingCaptchaCallbackHandler extends MissingCaptchaCallbackHandler {

    }
}
