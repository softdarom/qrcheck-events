package ru.softdarom.qrcheck.events.dao.access.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.softdarom.qrcheck.events.dao.access.OptionAccessService;
import ru.softdarom.qrcheck.events.dao.repository.OptionRepository;
import ru.softdarom.qrcheck.events.exception.NotFoundException;

import javax.transaction.Transactional;

@Service
public class OptionAccessServiceImpl implements OptionAccessService {

    private final OptionRepository optionRepository;

    @Autowired
    OptionAccessServiceImpl(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    @Override
    public boolean isEventHasOptions(Long eventId) {
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
        if (option.getAvailableQuantity() - quantity >= 0) {
            option.setAvailableQuantity(option.getAvailableQuantity() - quantity);
            optionRepository.save(option);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean unbookedOption(Long optionId, Integer quantity) {
        Assert.notNull(optionId, "The 'optionId' must not be null!");
        Assert.notNull(quantity, "The 'quantity' must not be null!");
        var option = optionRepository.findById(optionId)
                .orElseThrow(() -> new NotFoundException("Not found option with id: " + optionId));
        option.setAvailableQuantity(option.getAvailableQuantity() + quantity);
        optionRepository.save(option);
        return true;
    }
}
