package clear.solutions.assignment.controllers.handlers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Response {

    private String message;

    public static ResponseEntity<Response> builder(Exception e, HttpStatus status) {
        return new ResponseEntity<>(new Response(e.getMessage()), status);
    }

}
