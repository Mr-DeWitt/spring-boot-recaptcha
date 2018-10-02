package com.szityu.oss.spring.recaptcha.lib;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static lombok.AccessLevel.PRIVATE;

/**
 * This class will verify the reCAPTCHA value at Google.
 *
 * @author Szilard Laszlo Fodor
 */
@Getter
@AllArgsConstructor
@Builder
@ToString
@FieldDefaults(makeFinal = true, level = PRIVATE)
class RecaptchaValidator {
    static RestTemplate restTemplate = new RestTemplate();
    static String validationUrlBase = "https://www.google.com/recaptcha/api/siteverify";

    String secret;
    String userCaptchaResponse;
    String remoteIp;

    RecaptchaResponse validate() {

        String validationUrl = validationUrlBase +
                "?secret=" + secret +
                "&response=" + userCaptchaResponse +
                "&remoteip=" + remoteIp;

        return restTemplate.postForObject(URI.create(validationUrl), null, RecaptchaResponse.class);
    }
}
