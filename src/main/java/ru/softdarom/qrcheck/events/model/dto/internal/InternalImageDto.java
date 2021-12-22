package ru.softdarom.qrcheck.events.model.dto.internal;

import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Generated
public class InternalImageDto {

    private Long id;

    private Long externalImageId;

    private Boolean cover;

    public InternalImageDto(Long externalImageId, Boolean cover) {
        this.externalImageId = externalImageId;
        this.cover = cover;
    }
}