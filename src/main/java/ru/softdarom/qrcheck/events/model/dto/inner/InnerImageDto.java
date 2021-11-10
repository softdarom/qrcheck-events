package ru.softdarom.qrcheck.events.model.dto.inner;

import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Generated
public class InnerImageDto {

    private Long id;

    private Long externalImageId;

    private Boolean cover;

    public InnerImageDto(Long externalImageId, Boolean cover) {
        this.externalImageId = externalImageId;
        this.cover = cover;
    }
}