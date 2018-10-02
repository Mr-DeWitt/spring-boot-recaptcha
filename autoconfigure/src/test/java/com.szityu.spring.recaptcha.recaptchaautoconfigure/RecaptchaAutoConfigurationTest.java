package com.szityu.spring.recaptcha.recaptchaautoconfigure;

import com.szityu.spring.recaptcha.lib.RecaptchaValidatorCallbackFilter;
import com.szityu.spring.recaptcha.lib.RecaptchaValidatorFilter;
import com.szityu.spring.recaptcha.lib.callbackhandler.InvalidCaptchaCallbackHandler;
import com.szityu.spring.recaptcha.lib.callbackhandler.MissingCaptchaCallbackHandler;
import com.szityu.spring.recaptcha.lib.callbackhandler.ValidCaptchaCallbackHandler;
import com.szityu.spring.recaptcha.recaptchaautoconfigure.configurations.InvalidCaptchaCallbackHandlerOverrideConfiguration;
import com.szityu.spring.recaptcha.recaptchaautoconfigure.configurations.InvalidCaptchaCallbackHandlerOverrideConfiguration.OwnInvalidCaptchaCallbackHandler;
import com.szityu.spring.recaptcha.recaptchaautoconfigure.configurations.MissingCaptchaCallbackHandlerOverrideConfiguration;
import com.szityu.spring.recaptcha.recaptchaautoconfigure.configurations.MissingCaptchaCallbackHandlerOverrideConfiguration.OwnMissingCaptchaCallbackHandler;
import com.szityu.spring.recaptcha.recaptchaautoconfigure.configurations.RecaptchaFilterOverrideConfiguration;
import com.szityu.spring.recaptcha.recaptchaautoconfigure.configurations.RecaptchaFilterOverrideConfiguration.OwnRecaptchaValidatorFilter;
import com.szityu.spring.recaptcha.recaptchaautoconfigure.configurations.ValidCaptchaCallbackHandlerOverrideConfiguration;
import com.szityu.spring.recaptcha.recaptchaautoconfigure.configurations.ValidCaptchaCallbackHandlerOverrideConfiguration.OwnValidCaptchaCallbackHandler;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Szilard Laszlo Fodor
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RecaptchaAutoConfigurationTest {

    private final WebApplicationContextRunner webContextRunner = new WebApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(RecaptchaAutoConfiguration.class));
    private final ApplicationContextRunner nonWebContextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(RecaptchaAutoConfiguration.class));

    @Test
    public void voidtTestRecaptchaFilterBeanExistsForWebContextAndRequiredProperties() {
        this.webContextRunner
                .withPropertyValues("recaptcha.protected-urls=/protected-url/**", "recaptcha.secret=top-secret")
                .run((context) -> {
                    val autoConfig = context.getBean(RecaptchaAutoConfiguration.class);

                    assertThat(context).hasSingleBean(RecaptchaValidatorFilter.class);
                    assertThat(context.getBean(RecaptchaValidatorFilter.class))
                            .isSameAs(autoConfig.recaptchaValidatorFilter(autoConfig.recaptchaConfigProperties(), autoConfig.missingCaptchaCallbackHandler(), autoConfig.validCaptchaCallbackHandler(), autoConfig.invalidCaptchaCallbackHandler()))
                            .isExactlyInstanceOf(RecaptchaValidatorCallbackFilter.class);
                });
    }

    @Test
    public void testRecaptchaFilterBeanNotExistsForNonWeb() {
        this.nonWebContextRunner
                .withPropertyValues("recaptcha.protected-urls=/protected-url/**", "recaptcha.secret=top-secret")
                .run((context) -> assertThat(context).doesNotHaveBean(RecaptchaValidatorFilter.class));
    }

    @Test
    public void testRecaptchaFilterBeanNotExistsForMissingProperty() {
        this.webContextRunner
                .run((context) -> assertThat(context).doesNotHaveBean(RecaptchaValidatorFilter.class));
    }

    @Test
    public void testRecaptchaFilterBeanFailedToCreateForMissingSecret() {
        this.webContextRunner
                .withPropertyValues("recaptcha.protected-urls=/protected-url/**")
                .run((context) -> assertThat(context).hasFailed().getFailure().hasRootCauseInstanceOf(IllegalArgumentException.class));
    }

    @Test
    public void testRecaptchaFilterBeanOverride() {
        this.webContextRunner
                .withPropertyValues("recaptcha.protected-urls=/protected-url/**", "recaptcha.secret=top-secret")
                .withUserConfiguration(RecaptchaFilterOverrideConfiguration.class)
                .run((context) -> {
                    val autoConfig = context.getBean(RecaptchaAutoConfiguration.class);
                    val overrideConfig = context.getBean(RecaptchaFilterOverrideConfiguration.class);

                    assertThat(context).hasSingleBean(RecaptchaValidatorFilter.class);
                    assertThat(context.getBean(RecaptchaValidatorFilter.class))
                            .isSameAs(overrideConfig.recaptchaValidatorFilter(autoConfig.recaptchaConfigProperties()))
                            .isExactlyInstanceOf(OwnRecaptchaValidatorFilter.class);
                });
    }

    @Test
    public void testMissingRecaptchaFilterCallbackBeanOverride() {
        this.webContextRunner
                .withPropertyValues("recaptcha.protected-urls=/protected-url/**", "recaptcha.secret=top-secret")
                .withUserConfiguration(MissingCaptchaCallbackHandlerOverrideConfiguration.class)
                .run((context) -> {
                    val overrideConfig = context.getBean(MissingCaptchaCallbackHandlerOverrideConfiguration.class);

                    assertThat(context).hasSingleBean(MissingCaptchaCallbackHandler.class);
                    assertThat(context.getBean(MissingCaptchaCallbackHandler.class))
                            .isSameAs(overrideConfig.missingCaptchaCallbackHandler())
                            .isExactlyInstanceOf(OwnMissingCaptchaCallbackHandler.class);
                    assertThat(context).getBean(RecaptchaValidatorCallbackFilter.class)
                            .hasFieldOrPropertyWithValue("missingCaptchaCallbackHandler", context.getBean(OwnMissingCaptchaCallbackHandler.class));
                });
    }

    @Test
    public void testValidRecaptchaFilterCallbackBeanOverride() {
        this.webContextRunner
                .withPropertyValues("recaptcha.protected-urls=/protected-url/**", "recaptcha.secret=top-secret")
                .withUserConfiguration(ValidCaptchaCallbackHandlerOverrideConfiguration.class)
                .run((context) -> {
                    val overrideConfig = context.getBean(ValidCaptchaCallbackHandlerOverrideConfiguration.class);

                    assertThat(context).hasSingleBean(ValidCaptchaCallbackHandler.class);
                    assertThat(context.getBean(ValidCaptchaCallbackHandler.class))
                            .isSameAs(overrideConfig.validCaptchaCallbackHandler())
                            .isExactlyInstanceOf(OwnValidCaptchaCallbackHandler.class);
                    assertThat(context).getBean(RecaptchaValidatorCallbackFilter.class)
                            .hasFieldOrPropertyWithValue("validCaptchaCallbackHandler", context.getBean(OwnValidCaptchaCallbackHandler.class));
                });
    }

    @Test
    public void testInvalidRecaptchaFilterCallbackBeanOverride() {
        this.webContextRunner
                .withPropertyValues("recaptcha.protected-urls=/protected-url/**", "recaptcha.secret=top-secret")
                .withUserConfiguration(InvalidCaptchaCallbackHandlerOverrideConfiguration.class)
                .run((context) -> {
                    val overrideConfig = context.getBean(InvalidCaptchaCallbackHandlerOverrideConfiguration.class);

                    assertThat(context).hasSingleBean(InvalidCaptchaCallbackHandler.class);
                    assertThat(context.getBean(InvalidCaptchaCallbackHandler.class))
                            .isSameAs(overrideConfig.invalidCaptchaCallbackHandler())
                            .isExactlyInstanceOf(OwnInvalidCaptchaCallbackHandler.class);
                    assertThat(context).getBean(RecaptchaValidatorCallbackFilter.class)
                            .hasFieldOrPropertyWithValue("invalidCaptchaCallbackHandler", context.getBean(OwnInvalidCaptchaCallbackHandler.class));
                });
    }
}
