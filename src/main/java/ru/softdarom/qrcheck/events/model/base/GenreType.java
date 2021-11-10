package ru.softdarom.qrcheck.events.model.base;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum GenreType {

    SONGWRITING("Авторская песня"),
    BLACK_METAL("Блэк-металл"),
    BLUES("Блюз"),
    GRINDCORE("Грайндкор"),
    JAZZ("Джаз"),
    INDUSTRIAL("Индастриал"),
    COUNTRY("Кантри"),
    CLASSICAL_MUSIC("Классическая музыка"),
    LATINO("Латинос"),
    FOLK_MUSIC("Народная музыка"),
    NEW_AGE("Нью-эйдж"),
    POP("Поп"),
    POP_ROCK("Поп-Рок"),
    RNB("Ритм-н-Блюз"),
    ROCK("Рок"),
    ROCK_N_ROLL("Рок-н-Ролл"),
    ROMANCE("Романс"),
    RAP("Рэп"),
    SYNTHPOP("Синти-Поп"),
    SOUL("Соул"),
    TECHNO("Техно"),
    TRASH_METAL("Трэш-Металл"),
    FOLK("Фолк"),
    HEAVY_METAL("Хэви-Металл"),
    CHANSON("Шансон"),
    ELECTRO("Электро"),
    ELECTRONIC_MUSIC("Электронная музыка"),
    AMBIENT("Эмбиент"),
    ETHERIAL("Этериал");

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