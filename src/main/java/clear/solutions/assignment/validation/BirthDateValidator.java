package clear.solutions.assignment.validation;

import clear.solutions.assignment.controllers.exceptions.InvalidBirthDateException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Component
public class BirthDateValidator implements ConstraintValidator<BirthDateValidation, Date> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${age.min}")
    private int minAge;

    @Override
    public boolean isValid(Date birthDate, ConstraintValidatorContext context) {

        Calendar instance = GregorianCalendar.getInstance();

        if (birthDate.before(instance.getTime())) {
            LocalDate localDate = birthDate.toLocalDate();
            if (instance.get(Calendar.YEAR) - localDate.getYear() > minAge) {
                return true;
            } else if (instance.get(Calendar.YEAR) - localDate.getYear() == minAge) {
                if (instance.get(Calendar.MONTH) + 1 > localDate.getMonthValue()) {
                    return true;
                } else if (instance.get(Calendar.MONTH) + 1 == localDate.getMonthValue()) {
                    if (instance.get(Calendar.DAY_OF_MONTH) > localDate.getDayOfMonth()) {
                        return true;
                    }
                }
            }
        } else {
            logger.info("Invalid birth date {}", birthDate);
            throw new InvalidBirthDateException("Invalid birth date " + birthDate);
        }

        logger.info("User is too young - date {}", birthDate);
        return false;
    }

}
