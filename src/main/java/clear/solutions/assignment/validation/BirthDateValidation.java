package clear.solutions.assignment.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = {BirthDateValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BirthDateValidation {

    String message() default "Invalid birth date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
