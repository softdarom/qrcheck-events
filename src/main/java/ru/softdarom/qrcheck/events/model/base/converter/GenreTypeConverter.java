package ru.softdarom.qrcheck.events.model.base.converter;

import ru.softdarom.qrcheck.events.model.base.GenreType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class GenreTypeConverter implements AttributeConverter<GenreType, String> {

    @Override
    public String convertToDatabaseColumn(GenreType type) {
        return type.getType();
    }

    @Override
    public GenreType convertToEntityAttribute(String type) {
        return GenreType.typeOf(type);
    }
}