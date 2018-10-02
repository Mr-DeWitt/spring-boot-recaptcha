package com.szityu.oss.spring.recaptcha.autoconfigure;

import com.szityu.oss.spring.recaptcha.lib.callbackhandler.MissingCaptchaCallbackHandler;
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
