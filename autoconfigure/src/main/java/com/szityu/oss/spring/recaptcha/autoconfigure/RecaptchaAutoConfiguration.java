package com.szityu.oss.spring.recaptcha.autoconfigure;

import com.szityu.oss.spring.recaptcha.lib.RecaptchaConfigProperties;
import com.szityu.oss.spring.recaptcha.lib.RecaptchaValidatorCallbackFilter;
import com.szityu.oss.spring.recaptcha.lib.RecaptchaValidatorFilter;
import com.szityu.oss.spring.recaptcha.lib.callbackhandler.InvalidCaptchaCallbackHandler;
import com.szityu.oss.spring.recaptcha.lib.callbackhandler.MissingCaptchaCallbackHandler;
import com.szityu.oss.spring.recaptcha.lib.callbackhandler.ValidCaptchaCallbackHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The auto configuration class of reCAPTCHA integration for Spring Boot.
 * It will be triggered if <b>recaptcha.protected-urls</b> parameter is set.
 */

@Slf4j
@Configuration
@ConditionalOnProperty(name = "recaptcha.protected-urls")
@ConditionalOnWebApplication
@AutoConfigureAfter(value = WebMvcAutoConfiguration.class)
@EnableConfigurationProperties
public class RecaptchaAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "recaptcha")
    public RecaptchaConfigProperties recaptchaConfigProperties() {
        return new RecaptchaConfigProperties();
    }

    /**
     * Callback for handling request which has no proper captcha response header.
     * If you want to override the default behaviour define this bean in your application.
     *
     * @return The callback handler.
     */
    @Bean
    @ConditionalOnMissingBean
    public MissingCaptchaCallbackHandler missingCaptchaCallbackHandler() {
        return new MissingCaptchaCallbackHandler();
    }

    /**
     * Callback for handling request which contains a valid captcha response header.
     * If you want to override the default behaviour define this bean in your application.
     *
     * @return The callback handler.
     */
    @Bean
    @ConditionalOnMissingBean
    public ValidCaptchaCallbackHandler validCaptchaCallbackHandler() {
        return new ValidCaptchaCallbackHandler();
    }

    /**
     * Callback for handling request which contains a invalid captcha response header.
     * If you want to override the default behaviour define this bean in your application.
     *
     * @return The callback handler.
     */
    @Bean
    @ConditionalOnMissingBean
    public InvalidCaptchaCallbackHandler invalidCaptchaCallbackHandler() {
        return new InvalidCaptchaCallbackHandler();
    }

    /**
     * The web filter which will be used by Spring to validate protected URLs with captcha.
     * By default this filter works with callback handlers, so if you want to override some simple situation e.g.:
     * missing / invalid / valid captcha, then define the proper handler class as a bean in your application.
     * If you need a more complex customization, then define a {@link RecaptchaValidatorFilter} bean in your application and override it's methods.
     *
     * @return A descendant of the base {@link RecaptchaValidatorFilter} which works with callback handlers for easier customisation.
     * @see MissingCaptchaCallbackHandler
     * @see ValidCaptchaCallbackHandler
     * @see InvalidCaptchaCallbackHandler
     */
    @Bean
    @ConditionalOnMissingBean
    public RecaptchaValidatorFilter recaptchaValidatorFilter(
            RecaptchaConfigProperties reCaptchaConfigProperties,
            MissingCaptchaCallbackHandler missingCaptchaCallbackHandler,
            ValidCaptchaCallbackHandler validCaptchaCallbackHandler,
            InvalidCaptchaCallbackHandler invalidCaptchaCallbackHandler) {
        return new RecaptchaValidatorCallbackFilter(recaptchaConfigProperties(), missingCaptchaCallbackHandler, validCaptchaCallbackHandler, invalidCaptchaCallbackHandler);
    }

}
