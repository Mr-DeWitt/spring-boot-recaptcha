package com.szityu.spring.recaptcha.lib;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

/**
 * This is a simple DTO like class to avoid programmatic deserialization of Google's reCAPTCHA verification response.
 *
 * @author Szilard Laszlo Fodor
 */
@Getter
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
class RecaptchaResponse {
    boolean success;
}
