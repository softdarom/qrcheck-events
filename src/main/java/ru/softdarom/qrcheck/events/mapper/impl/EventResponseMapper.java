package ru.softdarom.qrcheck.events.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.softdarom.qrcheck.events.mapper.AbstractDtoMapper;
import ru.softdarom.qrcheck.events.model.dto.ImageDto;
import ru.softdarom.qrcheck.events.model.dto.inner.InnerEventDto;
import ru.softdarom.qrcheck.events.model.dto.response.EventResponse;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.function.BiConsumer;

@Component
@Transactional
public class EventResponseMapper extends AbstractDtoMapper<EventResponse, InnerEventDto> {

    public EventResponseMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected void setupMapper() {
        modelMapper.createTypeMap(sourceClass, destinationClass);
        modelMapper
                .createTypeMap(destinationClass, sourceClass)
                .addMappings(mapping -> mapping.map(InnerEventDto::getType, EventResponse::setEvent))
                .setPostConverter(toSourceConverter(new SourceConverter()));
    }

    public static class SourceConverter implements BiConsumer<InnerEventDto, EventResponse> {

        @Override
        public void accept(InnerEventDto destination, EventResponse source) {
            source.setActual(isActual(destination));
            source.setCover(coverStub());
            source.setImages(imagesStub());
        }

        private Boolean isActual(InnerEventDto destination) {
            return destination.getOverDate().isAfter(LocalDateTime.now()) && !destination.getDraft();
        }

        private ImageDto coverStub() {
            var dto = new ImageDto();
            dto.setId(1L);
            dto.setContent("https://www.golddisk.ru/goods_img/70/70501.jpg");
            dto.setFormat("JPG");
            return dto;
        }

        private Collection<ImageDto> imagesStub() {
            var dto = new ImageDto();
            dto.setId(2L);
            dto.setContent("https://static01.nyt.com/images/2015/04/19/arts/19KURT1/19KURT1-superJumbo.jpg?quality=75&auto=webp");
            dto.setFormat("JPG");
            return Set.of(dto);
        }
    }
}