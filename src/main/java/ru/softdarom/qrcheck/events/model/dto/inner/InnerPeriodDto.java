package ru.softdarom.qrcheck.events.model.dto.inner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import ru.softdarom.qrcheck.events.util.JsonHelper;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
public class InnerPeriodDto {

    private LocalDate startDate;

    private LocalTime startTime;

    @Override
    public String toString() {
        return JsonHelper.asJson(this);
    }
}