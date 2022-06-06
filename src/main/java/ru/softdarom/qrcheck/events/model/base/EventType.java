package ru.softdarom.qrcheck.events.model.base;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
@Schema(implementation = EventType.class, type = "integer")
public enum EventType {

    CONCERT("Концерт", 1),
    OPERA("Опера", 2),
    OPERETTA("Оперетта", 3),
    MUSICAL("Мюзикл", 4),
    SPECTACLE("Спектакль", 5),
    TALK_SHOW("Ток-Шоу", 6),
    FESTIVAL("Фестиваль", 7),
    FLASH_MOB("Флешмоб", 8),
    SHAW("Шоу", 9),
    FAIR("Ярмарка", 10);

    private final String type;

    private final Integer code;

    public static EventType typeOf(String type) {
        return EnumSet.allOf(EventType.class)
                .stream()
                .filter(it -> Objects.equals(it.type, type))
                .findFirst()
                .orElseThrow();
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static EventType codeOf(Integer code) {
        return EnumSet.allOf(EventType.class)
                .stream()
                .filter(it -> Objects.equals(it.code, code))
                .findFirst()
                .orElseThrow();
    }

    @JsonValue
    public Integer getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.valueOf(type);
    }
}