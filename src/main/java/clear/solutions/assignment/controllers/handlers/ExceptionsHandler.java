package clear.solutions.assignment.controllers.handlers;

import clear.solutions.assignment.controllers.exceptions.IllegalEmailFormatException;
import clear.solutions.assignment.controllers.exceptions.InvalidBirthDateException;
import clear.solutions.assignment.controllers.exceptions.InvalidDateRangeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(IllegalEmailFormatException.class)
    public ResponseEntity<Response> illegalEmailPatternHandler(IllegalEmailFormatException e) {
        return Response.builder(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<Response> invalidDateRangeHandler(InvalidDateRangeException e) {
        return Response.builder(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidBirthDateException.class)
    public ResponseEntity<Response> invalidBirthDateHandler(InvalidBirthDateException e) {
        return Response.builder(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Response> httpRequestMethodNotSupportedHandler(HttpRequestMethodNotSupportedException e) {
        return Response.builder(e, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response> httpMessageNotReadableHandler(HttpMessageNotReadableException e) {
        return Response.builder(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> methodArgumentNotValidHandler(MethodArgumentNotValidException e) {
        return Response.builder(e, HttpStatus.BAD_REQUEST);
    }

}
