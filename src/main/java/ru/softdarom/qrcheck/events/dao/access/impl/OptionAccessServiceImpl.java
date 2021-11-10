package ru.softdarom.qrcheck.events.dao.access.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.softdarom.qrcheck.events.dao.access.OptionAccessService;
import ru.softdarom.qrcheck.events.dao.repository.OptionRepository;

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
}
