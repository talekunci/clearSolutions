package clear.solutions.assignment.validation;

import clear.solutions.assignment.entities.DateRange;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.sql.Date;
import java.util.GregorianCalendar;

@SpringBootTest
public class DateRangeValidatorTests {

    @Mock
    private ConstraintValidatorContext context;

    @InjectMocks
    private DateRangeValidator validator;

    private Calendar calendar;

    @BeforeEach
    public void initialize() {
        calendar = GregorianCalendar.getInstance();
    }

    @Test
    public void validRangeTest() {
        Date to = new Date(calendar.getTimeInMillis());
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
        Date from = new Date(calendar.getTimeInMillis());

        Assertions.assertTrue(validator.isValid(new DateRange(from, to), context));
    }

    @Test
    public void invalidRangeTest() {
        Date from = new Date(calendar.getTimeInMillis());
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
        Date to = new Date(calendar.getTimeInMillis());

        Assertions.assertFalse(validator.isValid(new DateRange(from, to), context));
    }

}
