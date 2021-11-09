package ru.softdarom.qrcheck.events.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.softdarom.qrcheck.events.config.property.TaxProperties;
import ru.softdarom.qrcheck.events.dao.entity.TicketEntity;
import ru.softdarom.qrcheck.events.mapper.AbstractDtoMapper;
import ru.softdarom.qrcheck.events.model.dto.inner.InnerTickerDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiConsumer;

@Component
public class InnerTicketDtoMapper extends AbstractDtoMapper<TicketEntity, InnerTickerDto> {
    //ToDo https://softdarom.myjetbrains.com/youtrack/issue/QRC-55
    private final TaxProperties properties;

    protected InnerTicketDtoMapper(ModelMapper modelMapper, TaxProperties properties) {
        super(modelMapper);
        this.properties = properties;
    }

    @Override
    protected void setupMapper() {
        modelMapper
                .createTypeMap(sourceClass, destinationClass)
                .addMappings(mapping -> mapping.skip(InnerTickerDto::setPrice))
                .setPostConverter(toDestinationConverter(new DestinationConverter()));
        modelMapper
                .createTypeMap(destinationClass, sourceClass)
                .addMappings(mapping -> mapping.map(InnerTickerDto::getQuantity, TicketEntity::setAvailableQuantity))
                .setPostConverter(toSourceConverter(new SourceConverter()));
    }

    public class SourceConverter implements BiConsumer<InnerTickerDto, TicketEntity> {

        @Override
        public void accept(InnerTickerDto destination, TicketEntity source) {
            source.setPrice(calculateTotalAmount(destination.getCost()));
        }

        private BigDecimal calculateTotalAmount(Double cost) {
            var costAsBigDecimal = BigDecimal.valueOf(cost);
            var taxSum = costAsBigDecimal.multiply(BigDecimal.valueOf(properties.getGeneralTax()));
            return costAsBigDecimal.add(taxSum);
        }
    }

    public static class DestinationConverter implements BiConsumer<TicketEntity, InnerTickerDto> {

        @Override
        public void accept(TicketEntity source, InnerTickerDto destination) {
            var scaledPrice = source.getPrice().setScale(0, RoundingMode.UP);
            destination.setPrice(scaledPrice.doubleValue());
        }
    }
}