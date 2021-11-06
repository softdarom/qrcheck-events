package ru.softdarom.qrcheck.events.model.base.converter;

import ru.softdarom.qrcheck.events.model.base.EventType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EventTypeConverter implements AttributeConverter<EventType, String> {

    @Override
    public String convertToDatabaseColumn(EventType type) {
        return type.getType();
    }

    @Override
    public EventType convertToEntityAttribute(String type) {
        return EventType.typeOf(type);
    }
}