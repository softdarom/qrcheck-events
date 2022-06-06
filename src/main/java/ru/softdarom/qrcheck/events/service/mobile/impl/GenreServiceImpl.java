package ru.softdarom.qrcheck.events.service.mobile.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.softdarom.qrcheck.events.model.base.GenreType;
import ru.softdarom.qrcheck.events.model.dto.LocaleTypeDto;
import ru.softdarom.qrcheck.events.service.mobile.LocaleTypeService;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service("genreService")
@Slf4j(topic = "SERVICE")
public class GenreServiceImpl implements LocaleTypeService<GenreType> {

    private final MessageSource messageSource;

    @Autowired
    GenreServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Set<LocaleTypeDto> getLocaleTypes(Locale locale) {
        return getLocaleTypes(EnumSet.allOf(GenreType.class), locale);
    }

    @Override
    public Set<LocaleTypeDto> getLocaleTypes(Collection<GenreType> types, Locale locale) {
        Assert.notNull(types, "The 'types' must not be null!");
        Assert.notNull(locale, "The 'locale' must not be null!");
        return types.stream()
                .map(it -> build(it, locale))
                .collect(Collectors.toSet());
    }

    @Override
    public LocaleTypeDto getLocaleType(GenreType type, Locale locale) {
        Assert.notNull(type, "The 'type' must not be null!");
        Assert.notNull(locale, "The 'locale' must not be null!");
        return build(type, locale);
    }

    private LocaleTypeDto build(GenreType type, Locale locale) {
        var message = messageSource.getMessage(type.name().toLowerCase(), null, locale);
        return new LocaleTypeDto(type.getCode(), message);
    }
}
