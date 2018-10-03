package com.szityu.oss.spring.recaptcha.autoconfigure;

import lombok.val;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

public class RecaptchaProfileExclusionCondition extends SpringBootCondition {
    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        val message = ConditionMessage.forCondition("RecaptchaProfileExclusionCondition");
        val excludedProfiles = getProfileExclusions(context);

        return excludedProfiles.stream()
                .filter(excludedProfile -> context.getEnvironment().acceptsProfiles(excludedProfile))
                .map(excludedProfile -> ConditionOutcome.noMatch(message.found("excluded profile").items(excludedProfile)))
                .findAny()
                .orElseGet(() -> ConditionOutcome.match(message.didNotFind("excluded profile", "excluded profiles").items(excludedProfiles)));
    }

    private Set<String> getProfileExclusions(ConditionContext context) {
        return ofNullable(context.getEnvironment().getProperty("recaptcha.excluded-profiles", String[].class))
                .map(Arrays::stream)
                .orElseGet(Stream::empty)
                .collect(toSet());
    }
}
