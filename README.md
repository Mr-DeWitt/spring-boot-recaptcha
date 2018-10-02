# Spring Boot reCAPTCHA starter

This project intended to make reCAPTCHA integration easier for Spring Boot projects through a custom starter and autoconfiguration.  

## Prerequisites

The starter's autoconfiguration will be triggered if the following requirements are met:
* The project runs in a web environment e.g.: via a simple spring web starter.
* The `recaptcha.protected-urls` property has been set.
* The reCAPTCHA maven starter project included into dependencies:
```xml
<dependencies>
    ...
    <dependency>
        <groupId>com.szityu.oss.spring.recaptcha</groupId>
        <artifactId>recaptcha-spring-boot-starter</artifactId>
        <packaging>jar</packaging>
        <version>1.0.0</version>
    </dependency>
    ...
</dependencies>

``` 

## Usage scenarios

### Simple usage

If you do not want to customize the reCAPTCHA validation behaviour just configure it from application properties.
At the moment the following properties can be configured:
* `recaptcha.secret` The secret key obtained from Google's [reCAPTCHA](https://www.google.com/recaptcha) service used for validating user sent captcha values.
* `recaptcha.protectedUrls` An Ant styled URL list of captcha protected resources separated by commas.
* `recaptcha.headerName` The header name in which the client needs to send the captcha check value. The default value is `g-recaptcha-response`.

**Example**

Place the following lines into your `application.yaml`
```xml
recaptcha:
    secret: your-very-secret-code-from-google
    protectedUrls: /protected/urls/**,/other/protected-url
``` 

That's all, you're good to go now, try to reach your protected URL-s. Without a valid reCAPTCHA response HTTP 400 error will be thrown by the server. 

### Customize server responses

If you want custom server responses for different scenarios, just define your own callback handler beans. At the moment the following callback handlers are supported:
* `ValidCaptchaCallbackHandler`: this handler will be called when a client sends a valid reCAPTCHA value.
* `InvalidCaptchaCallbackHandler`: this handler will be called when a client sends an invalid reCAPTCHA value.
* `MissingCaptchaCallbackHandler`: this handler will be called when a client does not send any reCAPTCHA value.

**Example**

If we want custom error handling.

```java
@Configuration
public class MyOwnCustomCallbackHandlers {
    
    @Bean
    public InvalidCaptchaCallbackHandler invalidCaptchaCallbackHandler () {
        return new InvalidCaptchaCallbackHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
                response.setStatus(418);
                response.getWriter().append("My own custom invalid captcha error response body.");
            }
        };
    }
    
    @Bean
    public MissingCaptchaCallbackHandler missingCaptchaCallbackHandler () {
        return new MissingCaptchaCallbackHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
                response.setStatus(418);
                response.getWriter().append("O!M!G! You didn't send captcha!");
            }
        };
    }
}
```

### Fully customized behaviour

If you have totally different opinion about how reCAPTCHA validation should be done (or just find a bug you are eager to solve), then define a bean with type `RecaptchaValidatorFilter`.
You can override a bunch of it's methods and customize it's basic behaviour.

**Example**

Note that this filter requires `RecaptchaConfigProperties` which can be simple autowired.

```java
@Configuration
public class IKnowBetterConfig {
    
    @Bean
    public RecaptchaValidatorFilter recaptchaValidatorFilter (RecaptchaConfigProperties reCaptchaConfigProperties) {
        return new RecaptchaValidatorFilter(reCaptchaConfigProperties) {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
                // My own implementation.
            }
        };
    }
}
```

See `RecaptchaValidatorFilter`'s & `RecaptchaValidatorCallbackFilter`'s Java doc for further information.

## Author

* **Szilard Laszlo Fodor** - [Mr-DeWitt](https://github.com/Mr-DeWitt)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details