package clear.solutions.assignment.validation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalEmailFormatException extends RuntimeException {

    public IllegalEmailFormatException() {
    }

    public IllegalEmailFormatException(String message) {
        super(message);
    }

    public IllegalEmailFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalEmailFormatException(Throwable cause) {
        super(cause);
    }

    public IllegalEmailFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
