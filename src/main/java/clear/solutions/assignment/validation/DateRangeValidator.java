package clear.solutions.assignment.validation;

import clear.solutions.assignment.entities.DateRange;
import clear.solutions.assignment.validation.exceptions.InvalidDateRangeException;
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
            throw new InvalidDateRangeException("Field date 'to' is null");
        }

        logger.info("The 'from' date is earlier than the 'to' date");
        return false;
    }
}
