package ru.softdarom.qrcheck.events.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.softdarom.qrcheck.events.mapper.AbstractDtoMapper;
import ru.softdarom.qrcheck.events.model.dto.inner.InnerEventDto;
import ru.softdarom.qrcheck.events.model.dto.request.EventRequest;

@Component
public class EventRequestMapper extends AbstractDtoMapper<EventRequest, InnerEventDto> {

    public EventRequestMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected void setupMapper() {
        modelMapper
                .createTypeMap(sourceClass, destinationClass)
                .addMappings(mapping -> mapping.map(src -> Boolean.FALSE, InnerEventDto::setDraft));
        modelMapper.createTypeMap(destinationClass, sourceClass);
    }
}