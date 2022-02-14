package ru.softdarom.qrcheck.events.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.softdarom.qrcheck.events.config.property.TaxProperties;
import ru.softdarom.qrcheck.events.dao.entity.EventEntity;
import ru.softdarom.qrcheck.events.dao.entity.EventTypeEntity;
import ru.softdarom.qrcheck.events.dao.entity.GenreEntity;
import ru.softdarom.qrcheck.events.dao.repository.EventTypeRepository;
import ru.softdarom.qrcheck.events.dao.repository.GenreRepository;
import ru.softdarom.qrcheck.events.mapper.AbstractDtoMapper;
import ru.softdarom.qrcheck.events.model.base.EventType;
import ru.softdarom.qrcheck.events.model.base.GenreType;
import ru.softdarom.qrcheck.events.model.dto.internal.InternalEventDto;
import ru.softdarom.qrcheck.events.model.dto.internal.InternalTicketDto;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Component
public class InternalEventDtoMapper extends AbstractDtoMapper<EventEntity, InternalEventDto> {

    private static final Long DEFAULT_OVER_HOURS = 2L;
    private static final BigDecimal DEFAULT_CURRENT_AMOUNT = BigDecimal.ZERO;

    //ToDo https://softdarom.myjetbrains.com/youtrack/issue/QRC-55
    private final TaxProperties properties;
    private final GenreRepository genreRepository;
    private final EventTypeRepository eventTypeRepository;
    private final InternalTicketDtoMapper internalTicketDtoMapper;
    private final InternalOptionDtoMapper internalOptionDtoMapper;
    private final InternalImageDtoMapper internalImageDtoMapper;

    protected InternalEventDtoMapper(ModelMapper modelMapper, TaxProperties properties,
                                     GenreRepository genreRepository, EventTypeRepository eventTypeRepository,
                                     InternalTicketDtoMapper internalTicketDtoMapper,
                                     InternalOptionDtoMapper internalOptionDtoMapper,
                                     InternalImageDtoMapper internalImageDtoMapper) {
        super(modelMapper);
        this.properties = properties;
        this.genreRepository = genreRepository;
        this.eventTypeRepository = eventTypeRepository;
        this.internalTicketDtoMapper = internalTicketDtoMapper;
        this.internalOptionDtoMapper = internalOptionDtoMapper;
        this.internalImageDtoMapper = internalImageDtoMapper;
    }

    @Override
    protected void setupMapper() {
        modelMapper
                .createTypeMap(sourceClass, destinationClass)
                .setPostConverter(toDestinationConverter(new DestinationConverter()));
        modelMapper
                .createTypeMap(destinationClass, sourceClass)
                .addMappings(mapping -> {
                    mapping.skip(EventEntity::setExternalUserId);
                    mapping.skip(EventEntity::setType);
                    mapping.skip(EventEntity::setGenres);
                })
                .setPostConverter(toSourceConverter(new SourceConverter()));
    }

    @Transactional
    public class SourceConverter implements BiConsumer<InternalEventDto, EventEntity> {

        @Override
        public void accept(InternalEventDto destination, EventEntity source) {
            if (isPreCreate(destination.getId())) {
                source.setExternalUserId(getExternalUserId());
                source.setCurrentAmount(DEFAULT_CURRENT_AMOUNT);
                source.setDraft(Boolean.TRUE);
            } else if (isEndCreate(destination.getDraft())) {
                source.setDraft(Boolean.FALSE);
                updateSource(destination, source);
            } else if (isEdit(destination.getDraft())) {
                updateSource(destination, source);
            }
        }

        private void updateSource(InternalEventDto destination, EventEntity source) {
            source.setExternalUserId(getExternalUserId());
            source.setCurrentAmount(DEFAULT_CURRENT_AMOUNT);
            source.setTotalAmount(calculateTotalAmount(destination.getTickets()));
            source.setOverDate(calculateOverDate(destination.getStartDateTime()));
            source.setType(getEvent(destination.getType()));
            source.setGenres(getGenres(destination.getGenres()));
        }

        private BigDecimal calculateTotalAmount(Collection<InternalTicketDto> tickets) {
            return tickets.stream()
                    .map(it -> {
                        var quantityAsBigDecimal = BigDecimal.valueOf(it.getQuantity());
                        //ToDo https://softdarom.myjetbrains.com/youtrack/issue/QRC-55
                        var totalSumWithoutTax = it.getCost().multiply(quantityAsBigDecimal);
                        var taxSum = totalSumWithoutTax.multiply(BigDecimal.valueOf(properties.getGeneralTax()));
                        return totalSumWithoutTax.add(taxSum);
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        private LocalDateTime calculateOverDate(LocalDateTime startDateTime) {
            return startDateTime.minusHours(DEFAULT_OVER_HOURS);
        }

        private Set<GenreEntity> getGenres(Collection<GenreType> types) {
            return genreRepository.findAllByNameIn(types);
        }

        private EventTypeEntity getEvent(EventType type) {
            return eventTypeRepository.findByName(type);
        }

        private Long getExternalUserId() {
            return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }

        private boolean isPreCreate(Long destinationId) {
            return Objects.isNull(destinationId);
        }

        private boolean isEndCreate(Boolean draft) {
            return Boolean.TRUE.equals(draft);
        }

        private boolean isEdit(Boolean draft) {
            return Boolean.FALSE.equals(draft);
        }
    }

    public class DestinationConverter implements BiConsumer<EventEntity, InternalEventDto> {

        @Override
        public void accept(EventEntity source, InternalEventDto destination) {
            if (Objects.isNull(source.getDraft()) || Boolean.TRUE.equals(source.getDraft())) {
                return;
            }

            destination.setType(source.getType().getName());
            destination.setGenres(source.getGenres().stream().map(GenreEntity::getName).collect(Collectors.toSet()));
            destination.setTickets(internalTicketDtoMapper.convertToDestinations(source.getTickets()));
            destination.setOptions(internalOptionDtoMapper.convertToDestinations(source.getOptions()));
            destination.setImages(internalImageDtoMapper.convertToDestinations(source.getImages()));
        }
    }
}