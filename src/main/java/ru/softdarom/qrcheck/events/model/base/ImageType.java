package ru.softdarom.qrcheck.events.model.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImageType {

    PHOTOGRAPHY(false),
    COVER(true);

    private final boolean cover;
}