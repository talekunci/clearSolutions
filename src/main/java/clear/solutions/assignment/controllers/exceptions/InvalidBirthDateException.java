package clear.solutions.assignment.controllers.exceptions;

import org.springframework.stereotype.Component;

@Component
public class InvalidBirthDateException extends RuntimeException {
    public InvalidBirthDateException() {
    }

    public InvalidBirthDateException(String message) {
        super(message);
    }

    public InvalidBirthDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidBirthDateException(Throwable cause) {
        super(cause);
    }

    public InvalidBirthDateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
