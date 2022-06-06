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
@Schema(implementation = GenreType.class, type = "integer")
public enum GenreType {

    SONGWRITING("Авторская песня", 1),
    BLACK_METAL("Блэк-Металл", 2),
    BLUES("Блюз", 3),
    GRINDCORE("Грайндкор", 4),
    JAZZ("Джаз", 5),
    INDUSTRIAL("Индастриал", 6),
    COUNTRY("Кантри", 7),
    CLASSICAL_MUSIC("Классическая музыка", 8),
    LATINO("Латинос", 9),
    FOLK_MUSIC("Народная музыка", 10),
    NEW_AGE("Нью-эйдж", 11),
    POP("Поп", 12),
    POP_ROCK("Поп-Рок", 13),
    RNB("Ритм-н-Блюз", 14),
    ROCK("Рок", 15),
    ROCK_N_ROLL("Рок-н-Ролл", 16),
    ROMANCE("Романс", 17),
    RAP("Рэп", 18),
    SYNTHPOP("Синти-Поп", 19),
    SOUL("Соул", 20),
    TECHNO("Техно", 21),
    TRASH_METAL("Трэш-Металл", 22),
    FOLK("Фолк", 23),
    HEAVY_METAL("Хэви-Металл", 24),
    CHANSON("Шансон", 25),
    ELECTRO("Электро", 26),
    ELECTRONIC_MUSIC("Электронная музыка", 27),
    AMBIENT("Эмбиент", 28),
    ETHERIAL("Этериал", 29);

    private final String type;

    private final Integer code;

    public static GenreType typeOf(String type) {
        return EnumSet.allOf(GenreType.class)
                .stream()
                .filter(it -> Objects.equals(it.type, type))
                .findFirst()
                .orElseThrow();
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static GenreType codeOf(Integer code) {
        return EnumSet.allOf(GenreType.class)
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