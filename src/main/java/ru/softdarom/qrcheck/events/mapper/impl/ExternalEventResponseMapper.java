package ru.softdarom.qrcheck.events.mapper.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.softdarom.qrcheck.events.mapper.AbstractDtoMapper;
import ru.softdarom.qrcheck.events.model.dto.external.response.EventInfoResponse;
import ru.softdarom.qrcheck.events.model.dto.internal.InternalEventDto;

@Component
@Slf4j(topic = "MAPPER")
public class ExternalEventResponseMapper extends AbstractDtoMapper<InternalEventDto, EventInfoResponse> {

    protected ExternalEventResponseMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected void setupMapper() {
        modelMapper
                .createTypeMap(sourceClass, destinationClass);
    }
}
