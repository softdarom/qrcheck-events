package ru.softdarom.qrcheck.events.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
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
import ru.softdarom.qrcheck.events.model.dto.inner.InnerEventDto;
import ru.softdarom.qrcheck.events.model.dto.inner.InnerPeriodDto;
import ru.softdarom.qrcheck.events.model.dto.inner.InnerTickerDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Component
public class InnerEventDtoMapper extends AbstractDtoMapper<EventEntity, InnerEventDto> {

    private static final Long DEFAULT_OVER_HOURS = 2L;
    private static final BigDecimal DEFAULT_CURRENT_AMOUNT = BigDecimal.ZERO;

    //ToDo https://softdarom.myjetbrains.com/youtrack/issue/QRC-55
    private final TaxProperties properties;
    private final GenreRepository genreRepository;
    private final EventTypeRepository eventTypeRepository;
    private final InnerTicketDtoMapper innerTicketDtoMapper;
    private final InnerOptionDtoMapper innerOptionDtoMapper;

    protected InnerEventDtoMapper(ModelMapper modelMapper, TaxProperties properties,
                                  GenreRepository genreRepository, EventTypeRepository eventTypeRepository,
                                  InnerTicketDtoMapper innerTicketDtoMapper, InnerOptionDtoMapper innerOptionDtoMapper) {
        super(modelMapper);
        this.properties = properties;
        this.genreRepository = genreRepository;
        this.eventTypeRepository = eventTypeRepository;
        this.innerTicketDtoMapper = innerTicketDtoMapper;
        this.innerOptionDtoMapper = innerOptionDtoMapper;
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
                    mapping.map(source -> source.getPeriod().getStartDate(), EventEntity::setStartDate);
                    mapping.map(source -> source.getPeriod().getStartTime(), EventEntity::setStartTime);
                })
                .setPostConverter(toSourceConverter(new SourceConverter()));
    }

    public class SourceConverter implements BiConsumer<InnerEventDto, EventEntity> {

        @Override
        public void accept(InnerEventDto destination, EventEntity source) {
            source.setExternalUserId((Long) getAuthentication().getPrincipal());
            updateSource(destination, source);
        }

        private void updateSource(InnerEventDto destination, EventEntity source) {
            if (Objects.isNull(destination.getId())) {
                return;
            }

            source.setTotalAmount(calculateTotalAmount(destination.getTickets()));
            source.setOverDate(calculateOverDate(destination.getPeriod().getStartDate(), destination.getPeriod().getStartTime()));
            source.setCurrentAmount(DEFAULT_CURRENT_AMOUNT);
            source.setType(getEvent(destination.getEvent()));
            source.setGenres(getGenres(destination.getGenres()));
        }

        private BigDecimal calculateTotalAmount(Collection<InnerTickerDto> tickets) {
            return tickets.stream()
                    .map(it -> {
                        var costAsBigDecimal = BigDecimal.valueOf(it.getCost());
                        var quantityAsBigDecimal = BigDecimal.valueOf(it.getQuantity());
                        //ToDo https://softdarom.myjetbrains.com/youtrack/issue/QRC-55
                        var totalSumWithoutTax = costAsBigDecimal.multiply(quantityAsBigDecimal);
                        var taxSum = totalSumWithoutTax.multiply(BigDecimal.valueOf(properties.getGeneralTax()));
                        return totalSumWithoutTax.add(taxSum);
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        private LocalDateTime calculateOverDate(LocalDate startDate, LocalTime startTime) {
            var startDateTime = LocalDateTime.of(startDate, startTime);
            return startDateTime.minusHours(DEFAULT_OVER_HOURS);
        }

        private Set<GenreEntity> getGenres(Collection<GenreType> types) {
            return genreRepository.findAllByNameIn(types);
        }

        private EventTypeEntity getEvent(EventType type) {
            return eventTypeRepository.findByName(type);
        }

        private Authentication getAuthentication() {
            return SecurityContextHolder.getContext().getAuthentication();
        }
    }

    public class DestinationConverter implements BiConsumer<EventEntity, InnerEventDto> {

        @Override
        public void accept(EventEntity source, InnerEventDto destination) {
            if (Objects.isNull(source.getDraft()) || source.getDraft()) {
                return;
            }

            destination.setEvent(source.getType().getName());
            destination.setGenres(source.getGenres().stream().map(GenreEntity::getName).collect(Collectors.toSet()));
            destination.setPeriod(new InnerPeriodDto(source.getStartDate(), source.getStartTime()));
            destination.setTickets(innerTicketDtoMapper.convertToDestinations(source.getTickets()));
            destination.setOptions(innerOptionDtoMapper.convertToDestinations(source.getOptions()));
        }
    }
}