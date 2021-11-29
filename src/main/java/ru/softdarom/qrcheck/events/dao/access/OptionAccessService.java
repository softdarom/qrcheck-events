package ru.softdarom.qrcheck.events.dao.access;

public interface OptionAccessService {

    boolean isEventHasOptions(Long eventId);

    Boolean bookOption(Long optionId, Integer quantity);

    Boolean unbookedOption(Long optionId, Integer quantity);
}
