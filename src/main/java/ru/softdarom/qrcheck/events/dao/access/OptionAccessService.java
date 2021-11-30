package ru.softdarom.qrcheck.events.dao.access;

public interface OptionAccessService {

    boolean hasEventOptions(Long eventId);

    Boolean bookOption(Long optionId, Integer quantity);

    Boolean unbookedOption(Long optionId, Integer quantity);
}
