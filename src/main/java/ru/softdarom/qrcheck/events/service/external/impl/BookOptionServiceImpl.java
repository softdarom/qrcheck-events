package ru.softdarom.qrcheck.events.service.external.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.softdarom.qrcheck.events.dao.access.OptionAccessService;
import ru.softdarom.qrcheck.events.exception.InvalidBookOperation;
import ru.softdarom.qrcheck.events.model.dto.external.BookedExternalDto;
import ru.softdarom.qrcheck.events.model.dto.external.OptionExternalDto;
import ru.softdarom.qrcheck.events.model.dto.inner.InnerOptionDto;
import ru.softdarom.qrcheck.events.service.external.BookOptionService;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "SERVICE")
public class BookOptionServiceImpl implements BookOptionService {

    private final OptionAccessService optionAccessService;

    @Autowired
    BookOptionServiceImpl(OptionAccessService optionAccessService) {
        this.optionAccessService = optionAccessService;
    }

    @Override
    @Transactional
    public Set<OptionExternalDto> bookOptions(Collection<InnerOptionDto> eventTickets, Map<Long, BookedExternalDto> bookItems) {
        var neededTicket = eventTickets.stream()
                .filter(it -> bookItems.containsKey(it.getId()))
                .collect(Collectors.toSet());
        checkCountOfNeededAndExternalItems(neededTicket, bookItems.keySet());
        return neededTicket.stream()
                .map(ticket -> bookOption(ticket, bookItems.get(ticket.getId())))
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public Set<OptionExternalDto> unbookedOptions(Collection<InnerOptionDto> eventTickets, Map<Long, BookedExternalDto> bookItems) {
        var neededTicket = eventTickets.stream()
                .filter(it -> bookItems.containsKey(it.getId()))
                .collect(Collectors.toSet());
        checkCountOfNeededAndExternalItems(neededTicket, bookItems.keySet());
        return neededTicket.stream()
                .map(ticket -> unbookedOption(ticket, bookItems.get(ticket.getId())))
                .collect(Collectors.toSet());
    }

    private void checkCountOfNeededAndExternalItems(Collection<?> neededItems, Collection<?> externalItems) {
        if (neededItems.size() != externalItems.size()) {
            throw new InvalidBookOperation("The number of options does not match the number of options in the event!");
        }
    }

    private OptionExternalDto bookOption(InnerOptionDto option, BookedExternalDto reqTicket) {
        if (!optionAccessService.bookOption(reqTicket.getId(), reqTicket.getQuantity())) {
            throw new InvalidBookOperation("There are not enough options with id: " + option.getId());
        } else {
            return getOptionInfo(option);
        }
    }

    private OptionExternalDto unbookedOption(InnerOptionDto option, BookedExternalDto reqTicket) {
        if (!optionAccessService.unbookedOption(reqTicket.getId(), reqTicket.getQuantity())) {
            throw new InvalidBookOperation("There are not enough options with id: " + option.getId());
        } else {
            return getOptionInfo(option);
        }
    }

    private OptionExternalDto getOptionInfo(InnerOptionDto option) {
        var optionResp = new OptionExternalDto();
        optionResp.setId(option.getId());
        optionResp.setName(option.getName());
        optionResp.setPrice(option.getPrice());
        return optionResp;
    }
}
