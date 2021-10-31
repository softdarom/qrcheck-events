package ru.softdarom.qrcheck.events.model.base;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum GenreType {

    ROCK("Рок"),
    ELECTRONICA("Электро");

    @JsonValue
    private final String type;

    public static GenreType typeOf(String type) {
        return EnumSet.allOf(GenreType.class)
                .stream()
                .filter(it -> Objects.equals(it.type, type))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public String toString() {
        return String.valueOf(type);
    }
}