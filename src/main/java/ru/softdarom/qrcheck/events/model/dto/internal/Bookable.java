package ru.softdarom.qrcheck.events.model.dto.internal;

import ru.softdarom.qrcheck.events.model.base.BookType;

import java.math.BigDecimal;

public interface Bookable {

    Long getId();

    BigDecimal getPrice();

    BookType getBookType();

}