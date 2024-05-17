package clear.solutions.assignment.controllers.exceptions;

import org.springframework.stereotype.Component;

@Component
public class InvalidDateRangeException extends RuntimeException {
    public InvalidDateRangeException() {
    }

    public InvalidDateRangeException(String message) {
        super(message);
    }

    public InvalidDateRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDateRangeException(Throwable cause) {
        super(cause);
    }

    public InvalidDateRangeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
