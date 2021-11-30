package ru.softdarom.qrcheck.events.model.dto.inner;

import ru.softdarom.qrcheck.events.model.base.BookType;

import java.math.BigDecimal;

public interface Bookable {

    Long getId();

    String getName();

    BigDecimal getPrice();

    BookType getBookType();

}