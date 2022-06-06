package ru.softdarom.qrcheck.events.service.mobile.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.softdarom.qrcheck.events.model.base.EventType;
import ru.softdarom.qrcheck.events.model.dto.LocaleTypeDto;
import ru.softdarom.qrcheck.events.service.mobile.LocaleTypeService;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service("eventTypeService")
@Slf4j(topic = "SERVICE")
public class EventTypeServiceImpl implements LocaleTypeService<EventType> {

    private final MessageSource messageSource;

    @Autowired
    EventTypeServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Set<LocaleTypeDto> getLocaleTypes(Locale locale) {
        return getLocaleTypes(EnumSet.allOf(EventType.class), locale);
    }

    @Override
    public Set<LocaleTypeDto> getLocaleTypes(Collection<EventType> types, Locale locale) {
        Assert.notNull(types, "The 'types' must not be null!");
        Assert.notNull(locale, "The 'locale' must not be null!");
        return types.stream()
                .map(it -> build(it, locale))
                .collect(Collectors.toSet());
    }

    @Override
    public LocaleTypeDto getLocaleType(EventType type, Locale locale) {
        Assert.notNull(type, "The 'type' must not be null!");
        Assert.notNull(locale, "The 'locale' must not be null!");
        return build(type, locale);
    }

    private LocaleTypeDto build(EventType type, Locale locale) {
        var message = messageSource.getMessage(type.name().toLowerCase(), null, locale);
        return new LocaleTypeDto(type.getCode(), message);
    }
}
