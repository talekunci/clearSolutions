package clear.solutions.assignment.validation;

import clear.solutions.assignment.controllers.exceptions.InvalidBirthDateException;
import clear.solutions.assignment.entities.DateRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DateRangeValidator implements ConstraintValidator<DateRangeValidation, DateRange> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean isValid(DateRange value, ConstraintValidatorContext context) {

        try {
            if (value.getFrom().before(value.getTo())) {
                return true;
            }
        } catch (NullPointerException e) {
            logger.error("Field date 'to' is null", e);
            throw new NullPointerException("Field date 'to' is null");
        }

        logger.info("The 'from' date is earlier than the 'to' date");
        throw new InvalidBirthDateException("The 'from' date is earlier than the 'to' date");
    }
}
