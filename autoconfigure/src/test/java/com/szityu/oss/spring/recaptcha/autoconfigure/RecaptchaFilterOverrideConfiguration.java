package com.szityu.oss.spring.recaptcha.autoconfigure;

import com.szityu.oss.spring.recaptcha.lib.RecaptchaConfigProperties;
import com.szityu.oss.spring.recaptcha.lib.RecaptchaValidatorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Szilard Laszlo Fodor
 */
@Configuration
public class RecaptchaFilterOverrideConfiguration {
    @Bean
    public RecaptchaValidatorFilter recaptchaValidatorFilter(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") RecaptchaConfigProperties recaptchaConfigProperties) {
        return new OwnRecaptchaValidatorFilter(recaptchaConfigProperties);
    }

    public static class OwnRecaptchaValidatorFilter extends RecaptchaValidatorFilter {
        OwnRecaptchaValidatorFilter(RecaptchaConfigProperties reCaptchaConfigProperties) {
            super(reCaptchaConfigProperties);
        }
    }
}
