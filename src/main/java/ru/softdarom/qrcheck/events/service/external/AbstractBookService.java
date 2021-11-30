package ru.softdarom.qrcheck.events.service.external;

import ru.softdarom.qrcheck.events.exception.InvalidBookOperation;
import ru.softdarom.qrcheck.events.model.dto.external.BookedExternalDto;
import ru.softdarom.qrcheck.events.model.dto.inner.Bookable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractBookService implements BookService {


    protected Map<Long, BookedExternalDto> getId2DtoMap(Collection<BookedExternalDto> itemRequest) {
        return itemRequest.stream().collect(Collectors.toMap(BookedExternalDto::getId, Function.identity()));
    }

    protected <T> Set<T> changeBookedStatuses(Collection<? extends Bookable> bookables, Map<Long, BookedExternalDto> bookItems,
                                              BiPredicate<Long, Integer> predicate, Function<Bookable, T> function) {
        var neededTicket = bookables.stream()
                .filter(it -> bookItems.containsKey(it.getId()))
                .collect(Collectors.toSet());
        checkCountOfNeededAndExternalItems(neededTicket, bookItems.keySet());
        return neededTicket.stream()
                .map(it -> changeBookedStatus(it, bookItems.get(it.getId()), predicate, function))
                .collect(Collectors.toSet());
    }

    protected <T extends Bookable, R> R changeBookedStatus(T bookable, BookedExternalDto dto, BiPredicate<Long, Integer> predicate, Function<T, R> function) {
        if (!predicate.test(dto.getId(), dto.getQuantity())) {
            var message = String.format("There are not enough bookable items (type is '%s') with id: %s", bookable.getBookType(), bookable.getId());
            throw new InvalidBookOperation(message);
        } else {
            return function.apply(bookable);
        }
    }

    protected void checkCountOfNeededAndExternalItems(Collection<? extends Bookable> neededItems, Collection<Long> externalItems) {
        if (neededItems.size() != externalItems.size()) {
            throw new InvalidBookOperation("The number of bookable items do not match the number of the items in the event!");
        }
    }
}