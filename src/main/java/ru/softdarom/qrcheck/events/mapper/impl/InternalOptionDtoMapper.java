package ru.softdarom.qrcheck.events.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.softdarom.qrcheck.events.config.property.TaxProperties;
import ru.softdarom.qrcheck.events.dao.entity.OptionEntity;
import ru.softdarom.qrcheck.events.mapper.AbstractDtoMapper;
import ru.softdarom.qrcheck.events.model.dto.internal.InternalOptionDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiConsumer;

@Component
public class InternalOptionDtoMapper extends AbstractDtoMapper<OptionEntity, InternalOptionDto> {

    //ToDo https://softdarom.myjetbrains.com/youtrack/issue/QRC-55
    private final TaxProperties properties;

    protected InternalOptionDtoMapper(ModelMapper modelMapper, TaxProperties properties) {
        super(modelMapper);
        this.properties = properties;
    }

    @Override
    protected void setupMapper() {
        modelMapper
                .createTypeMap(sourceClass, destinationClass)
                .addMappings(mapping -> mapping.skip(InternalOptionDto::setPrice))
                .setPostConverter(toDestinationConverter(new DestinationConverter()));
        modelMapper
                .createTypeMap(destinationClass, sourceClass)
                .addMappings(mapping -> mapping.map(InternalOptionDto::getQuantity, OptionEntity::setAvailableQuantity))
                .setPostConverter(toSourceConverter(new SourceConverter()));
    }

    public static class DestinationConverter implements BiConsumer<OptionEntity, InternalOptionDto> {

        @Override
        public void accept(OptionEntity source, InternalOptionDto destination) {
            var scaledPrice = source.getPrice().setScale(0, RoundingMode.UP);
            destination.setPrice(scaledPrice);
        }
    }

    public class SourceConverter implements BiConsumer<InternalOptionDto, OptionEntity> {

        @Override
        public void accept(InternalOptionDto destination, OptionEntity source) {
            source.setPrice(calculateTotalAmount(destination.getCost()));
        }

        private BigDecimal calculateTotalAmount(BigDecimal cost) {
            var taxSum = cost.multiply(BigDecimal.valueOf(properties.getGeneralTax()));
            return cost.add(taxSum);
        }
    }
}