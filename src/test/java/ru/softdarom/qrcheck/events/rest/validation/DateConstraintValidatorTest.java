package ru.softdarom.qrcheck.events.rest.validation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.softdarom.qrcheck.events.model.dto.PeriodDto;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("unit")
class DateConstraintValidatorTest {

    private DateConstraintValidator validator;

    @BeforeEach
    void setUp() {
        validator = new DateConstraintValidator();
    }

    @AfterEach
    void tearDown() {
        validator = null;
    }

    @Test
    void isValid() {
        assertTrue(validator.isValid(PeriodDto.builder()
                .startDate(LocalDate.now().atStartOfDay().plusDays(1).toLocalDate())
                .startTime(LocalTime.MIN).build(), null));

    }

    @Test
    void isInValid() {
        assertFalse(validator.isValid(PeriodDto.builder()
                .startDate(LocalDate.now().atStartOfDay().toLocalDate())
                .startTime(LocalTime.MIN).build(), null));
    }
}