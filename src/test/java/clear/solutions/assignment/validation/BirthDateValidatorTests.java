package clear.solutions.assignment.validation;

import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;


@SpringBootTest
class BirthDateValidatorTests {

    @Mock
    private ConstraintValidatorContext context;

    @InjectMocks
    private BirthDateValidator validator;

    private Calendar calendar;

    @Value("${age.min}")
    private int age;

    @SneakyThrows
    @BeforeEach
    public void initialize() {
        Field ageField = BirthDateValidator.class.getDeclaredField("minAge");
        ageField.setAccessible(true);
        ageField.set(validator, age);

        calendar = GregorianCalendar.getInstance();
    }

    @Test
    void validAgeTest() {
        calendar.set(calendar.get(Calendar.YEAR) - age, calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE) - 1);
        Date validDate = new Date(calendar.getTimeInMillis());

        Assertions.assertTrue(validator.isValid(validDate, context));
    }

    @Test
    void invalidAgeTest() {
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
        Date invalidDate = new Date(calendar.getTimeInMillis());

        Assertions.assertFalse(validator.isValid(invalidDate, context));
    }

}
