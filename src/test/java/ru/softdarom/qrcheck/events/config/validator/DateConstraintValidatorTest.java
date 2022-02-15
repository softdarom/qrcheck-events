package ru.softdarom.qrcheck.events.config.validator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.softdarom.qrcheck.events.model.dto.PeriodDto;
import ru.softdarom.qrcheck.events.test.tag.UnitTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@UnitTest
@DisplayName("DateConstraintValidator Unit Test")
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

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("isValid(): returns true when start date and time after now")
    void successfulIsValid() {
        assertTrue(validator.isValid(PeriodDto.builder()
                .startDate(LocalDate.now().atStartOfDay().plusDays(1).toLocalDate())
                .startTime(LocalTime.MIN).build(), null));

    }

    //  -----------------------   failure tests   -------------------------

    @Test
    @DisplayName("isValid(): returns false when start date and time before now")
    void isInValid() {
        assertFalse(validator.isValid(PeriodDto.builder()
                .startDate(LocalDate.now().atStartOfDay().toLocalDate())
                .startTime(LocalTime.MIN).build(), null));
    }
}