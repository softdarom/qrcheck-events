package ru.softdarom.qrcheck.events.mapper.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.softdarom.qrcheck.events.mapper.AbstractDtoMapper;
import ru.softdarom.qrcheck.events.model.dto.TicketDto;
import ru.softdarom.qrcheck.events.model.dto.inner.InnerTicketDto;

@Component
@Slf4j(topic = "MAPPER")
public class TicketResponseMapper extends AbstractDtoMapper<InnerTicketDto, TicketDto> {

    @Autowired
    TicketResponseMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected void setupMapper() {
        modelMapper.createTypeMap(sourceClass, destinationClass);
    }
}
