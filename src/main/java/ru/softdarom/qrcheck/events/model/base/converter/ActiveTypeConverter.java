package ru.softdarom.qrcheck.events.model.base.converter;

import ru.softdarom.qrcheck.events.model.base.ActiveType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ActiveTypeConverter implements AttributeConverter<ActiveType, Boolean> {

    @Override
    public Boolean convertToDatabaseColumn(ActiveType type) {
        return type.isActive();
    }

    @Override
    public ActiveType convertToEntityAttribute(Boolean active) {
        return ActiveType.activeOf(active);
    }
}