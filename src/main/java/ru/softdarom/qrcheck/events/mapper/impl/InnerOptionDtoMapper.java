package ru.softdarom.qrcheck.events.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.softdarom.qrcheck.events.config.property.TaxProperties;
import ru.softdarom.qrcheck.events.dao.entity.OptionEntity;
import ru.softdarom.qrcheck.events.mapper.AbstractDtoMapper;
import ru.softdarom.qrcheck.events.model.dto.inner.InnerOptionDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiConsumer;

@Component
public class InnerOptionDtoMapper extends AbstractDtoMapper<OptionEntity, InnerOptionDto> {

    //ToDo https://softdarom.myjetbrains.com/youtrack/issue/QRC-55
    private final TaxProperties properties;

    protected InnerOptionDtoMapper(ModelMapper modelMapper, TaxProperties properties) {
        super(modelMapper);
        this.properties = properties;
    }

    @Override
    protected void setupMapper() {
        modelMapper
                .createTypeMap(sourceClass, destinationClass)
                .addMappings(mapping -> mapping.skip(InnerOptionDto::setPrice))
                .setPostConverter(toDestinationConverter(new DestinationConverter()));
        modelMapper
                .createTypeMap(destinationClass, sourceClass)
                .addMappings(mapping -> mapping.map(InnerOptionDto::getQuantity, OptionEntity::setAvailableQuantity))
                .setPostConverter(toSourceConverter(new SourceConverter()));
    }

    public class SourceConverter implements BiConsumer<InnerOptionDto, OptionEntity> {

        @Override
        public void accept(InnerOptionDto destination, OptionEntity source) {
            source.setPrice(calculateTotalAmount(destination.getCost()));
        }

        private BigDecimal calculateTotalAmount(Double cost) {
            var costAsBigDecimal = BigDecimal.valueOf(cost);
            var taxSum = costAsBigDecimal.multiply(BigDecimal.valueOf(properties.getGeneralTax()));
            return costAsBigDecimal.add(taxSum);
        }
    }

    public static class DestinationConverter implements BiConsumer<OptionEntity, InnerOptionDto> {

        @Override
        public void accept(OptionEntity source, InnerOptionDto destination) {
            var scaledPrice = source.getPrice().setScale(0, RoundingMode.UP);
            destination.setPrice(scaledPrice.doubleValue());
        }
    }
}