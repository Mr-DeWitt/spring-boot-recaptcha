package com.szityu.oss.spring.recaptcha.lib;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

/**
 * This class contains the config parameters for integration reCAPTCHA into Spring Boot.
 *
 * @author Szilard Laszlo Fodor
 */
@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class RecaptchaConfigProperties {
    /**
     * The secret key obtained from Google's reCAPTCHA service used for validating user sent captcha values.
     */
    String secret;
    /**
     * An Ant styled URL list of captcha protected resources.
     */
    String[] protectedUrls;
    /**
     * The header name in which the client needs to send the captcha check value.
     */
    String headerName = "g-recaptcha-response";
    /**
     * A profile name list. If one of the profiles is active the reCAPTCHA validator filter won't be registered.
     * This is useful if you don't need reCAPTCHA validation during dev stage or integration testing.
     */
    String[] excludedProfiles;
}
