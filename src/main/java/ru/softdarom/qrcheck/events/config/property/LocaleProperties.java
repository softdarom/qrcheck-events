package ru.softdarom.qrcheck.events.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Validated
@ConfigurationProperties("spring.web.locale")
public class LocaleProperties {

    @NotEmpty
    private String standard;

    @NotEmpty
    private Set<String> supports;

    @NotEmpty
    private String encoding;

    @NotEmpty
    private Set<String> messagePaths;

    public Locale getStandard() {
        return new Locale(standard);
    }

    public Set<Locale> getSupports() {
        return supports
                .stream()
                .map(Locale::new)
                .collect(Collectors.toSet());
    }

    public String[] getMessagePaths() {
        return messagePaths.toArray(new String[0]);
    }
}
