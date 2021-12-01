package ru.softdarom.qrcheck.events.dao.access.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.softdarom.qrcheck.events.dao.access.OptionAccessService;
import ru.softdarom.qrcheck.events.dao.entity.OptionEntity;
import ru.softdarom.qrcheck.events.dao.repository.OptionRepository;
import ru.softdarom.qrcheck.events.exception.NotFoundException;

import javax.transaction.Transactional;

@Service
@Slf4j(topic = "ACCESS-SERVICE")
public class OptionAccessServiceImpl implements OptionAccessService {

    private final OptionRepository optionRepository;

    @Autowired
    OptionAccessServiceImpl(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    @Override
    public boolean hasEventOptions(Long eventId) {
        Assert.notNull(eventId, "The 'eventId' must not be null!");
        return optionRepository.isEventHasOptions(eventId);
    }

    @Override
    @Transactional
    public Boolean bookOption(Long optionId, Integer quantity) {
        Assert.notNull(optionId, "The 'optionId' must not be null!");
        Assert.notNull(quantity, "The 'quantity' must not be null!");
        var option = optionRepository.findById(optionId)
                .orElseThrow(() -> new NotFoundException("Not found option with id: " + optionId));
        if (canBook(option, quantity)) {
            LOGGER.info("An option (id: {}, count: {}) will be booked.", optionId, quantity);
            option.setAvailableQuantity(option.getAvailableQuantity() - quantity);
            optionRepository.save(option);
            return true;
        } else {
            LOGGER.warn(
                    "An option (id: {}, quantity: {}) can not be booked! Available quantity of options are '{}'",
                    optionId, quantity, option.getAvailableQuantity()
            );
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean unbookedOption(Long optionId, Integer quantity) {
        Assert.notNull(optionId, "The 'optionId' must not be null!");
        Assert.notNull(quantity, "The 'quantity' must not be null!");
        LOGGER.info("An option (id: {}, count: {}) will be unbooked.", optionId, quantity);
        var option = optionRepository.findById(optionId)
                .orElseThrow(() -> new NotFoundException("Not found option with id: " + optionId));
        option.setAvailableQuantity(option.getAvailableQuantity() + quantity);
        optionRepository.save(option);
        return true;
    }

    private boolean canBook(OptionEntity option, Integer bookingQuantity) {
        return option.getAvailableQuantity() - bookingQuantity >= 0;
    }
}
