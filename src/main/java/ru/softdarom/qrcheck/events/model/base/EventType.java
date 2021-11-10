package ru.softdarom.qrcheck.events.model.base;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum EventType {

    CONCERT("Концерт"),
    OPERA("Опера"),
    OPERETTA("Оперетта"),
    MUSICAL("Мюзикл"),
    SPECTACLE("Спектакль"),
    TALK_SHOW("Ток-Шоу"),
    FESTIVAL("Фестиваль"),
    FLASH_MOB("Флешмоб"),
    SHAW("Шоу"),
    FAIR("Ярмарка");

    @JsonValue
    private final String type;

    public static EventType typeOf(String type) {
        return EnumSet.allOf(EventType.class)
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