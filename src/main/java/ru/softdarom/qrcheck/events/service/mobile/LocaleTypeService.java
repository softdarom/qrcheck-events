package ru.softdarom.qrcheck.events.service.mobile;

import ru.softdarom.qrcheck.events.model.dto.LocaleTypeDto;

import java.util.Collection;
import java.util.Locale;
import java.util.Set;

public interface LocaleTypeService<E> {

    Set<LocaleTypeDto> getLocaleTypes(Locale locale);

    Set<LocaleTypeDto> getLocaleTypes(Collection<E> types, Locale locale);

    LocaleTypeDto getLocaleType(E type, Locale locale);
}
