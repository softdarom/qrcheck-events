package ru.softdarom.qrcheck.events.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.softdarom.qrcheck.events.mapper.AbstractDtoMapper;
import ru.softdarom.qrcheck.events.model.dto.internal.InternalEventDto;
import ru.softdarom.qrcheck.events.model.dto.request.EventRequest;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.BiConsumer;

@Component
public class EventRequestMapper extends AbstractDtoMapper<EventRequest, InternalEventDto> {

    public EventRequestMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected void setupMapper() {
        modelMapper
                .createTypeMap(sourceClass, destinationClass)
                .addMappings(mapping -> {
                    mapping.map(src -> Boolean.FALSE, InternalEventDto::setDraft);
                    mapping.map(EventRequest::getEvent, InternalEventDto::setType);
                })
                .setPostConverter(toDestinationConverter(new DestinationConverter()));
        modelMapper.createTypeMap(destinationClass, sourceClass);
    }

    public static class DestinationConverter implements BiConsumer<EventRequest, InternalEventDto> {

        @Override
        public void accept(EventRequest source, InternalEventDto destination) {
            setStartDateTime(source, destination);
        }

        private void setStartDateTime(EventRequest source, InternalEventDto destination) {
            if (Objects.nonNull(source) && Objects.nonNull(source.getPeriod())) {
                var period = source.getPeriod();
                var startDateTime = LocalDateTime.of(period.getStartDate(), period.getStartTime());
                destination.setStartDateTime(startDateTime);
            }
        }
    }
}