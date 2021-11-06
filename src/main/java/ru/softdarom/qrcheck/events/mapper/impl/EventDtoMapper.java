package ru.softdarom.qrcheck.events.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.softdarom.qrcheck.events.dao.entity.EventEntity;
import ru.softdarom.qrcheck.events.mapper.AbstractDtoMapper;
import ru.softdarom.qrcheck.events.model.dto.inner.EventDto;

@Component
public class EventDtoMapper extends AbstractDtoMapper<EventEntity, EventDto> {

    public EventDtoMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected void setupMapper() {
        modelMapper.createTypeMap(sourceClass, destinationClass);
        modelMapper.createTypeMap(destinationClass, sourceClass);
    }

}