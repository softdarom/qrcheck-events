package ru.softdarom.qrcheck.events.model.dto.inner;

import lombok.Data;
import lombok.Generated;
import ru.softdarom.qrcheck.events.util.JsonHelper;

@Data
@Generated
public class InnerOptionDto {

    private Long id;

    private String name;

    private Integer quantity;

    private Integer availableQuantity;

    private Double cost;

    private Double price;

    @Override
    public String toString() {
        return JsonHelper.asJson(this);
    }
}