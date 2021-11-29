package ru.softdarom.qrcheck.events.service.external;

import ru.softdarom.qrcheck.events.model.dto.external.BookedExternalDto;
import ru.softdarom.qrcheck.events.model.dto.external.OptionExternalDto;
import ru.softdarom.qrcheck.events.model.dto.inner.InnerOptionDto;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface BookOptionService {

    Set<OptionExternalDto> bookOptions(Collection<InnerOptionDto> eventTickets, Map<Long, BookedExternalDto> bookItems);

    Set<OptionExternalDto> unbookedOptions(Collection<InnerOptionDto> eventTickets, Map<Long, BookedExternalDto> bookItems);
}
