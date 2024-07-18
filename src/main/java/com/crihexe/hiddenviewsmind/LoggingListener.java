package com.crihexe.hiddenviewsmind;

import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

// from: https://gist.github.com/Christian-Oette/a0bb05cb703fea20d28bbf46b7ce7574
/**
 * Usage
 *  SpringApplication springApplication = new SpringApplication(MyApplication.class);
 *  springApplication.addListeners(new LoggingListener());
 *  springApplication.run(args);
 */
@Slf4j
public class LoggingListener implements ApplicationListener<ApplicationPreparedEvent> {

    private final List<String> SENSITIVE_PROPERTIES = List.of("password", "credential", "token", "user-id");

    @Override
    public void onApplicationEvent(final ApplicationPreparedEvent event) {
        logProperties(event.getApplicationContext());
    }

    public void logProperties(ApplicationContext applicationContext) {
        final Environment env = applicationContext.getEnvironment();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n====== Log all properties for testing ======\n")
                .append("Active profiles: ")
                .append(Arrays.toString(env.getActiveProfiles()))
                .append("\n");

        if (env instanceof AbstractEnvironment) {
            final MutablePropertySources sources = ((AbstractEnvironment) env).getPropertySources();
            StreamSupport.stream(sources.spliterator(), false)
                    .filter(ps -> ps instanceof EnumerablePropertySource)
                    .map(this::castToEnumerablePropertySource)
                    .map(EnumerablePropertySource::getPropertyNames)
                    .flatMap(Arrays::stream)
                    .forEach(prop -> appendProperty(env, prop, stringBuilder));
        } else {
            stringBuilder.append("Unable to read properties");
        }
        log.info(stringBuilder.toString());
    }

    private EnumerablePropertySource<?> castToEnumerablePropertySource(final PropertySource<?> propertySource) {
        return (EnumerablePropertySource<?>) propertySource;
    }

    private void appendProperty(final Environment env, final String prop, final StringBuilder stringBuilder) {
        boolean isASensitiveProperty = SENSITIVE_PROPERTIES.stream()
                .anyMatch(prop::contains);
        try {
            final String value;
            if (isASensitiveProperty) {
                value = StringUtils.abbreviate(env.getProperty(prop), 4);
            } else {
                value = env.getProperty(prop);
            }
            stringBuilder.append(String.format("%s: %s\n", prop, value));
        } catch (Exception ex) {
            stringBuilder.append(String.format("%s: %s\n", prop, ex.getMessage()));
        }

    }
}