package clear.solutions.assignment.controllers.handlers;

import clear.solutions.assignment.controllers.UserController;
import clear.solutions.assignment.controllers.exceptions.IllegalEmailFormatException;
import clear.solutions.assignment.controllers.exceptions.InvalidBirthDateException;
import clear.solutions.assignment.controllers.exceptions.InvalidDateRangeException;
import clear.solutions.assignment.entities.DateRange;
import clear.solutions.assignment.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;
import java.util.Calendar;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ExceptionHandlerTests {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Mock
    private UserController userController;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new ExceptionsHandler())
                .build();
    }

    @Test
    public void illegalEmailFormatExceptionTest() throws Exception {
        String email = "email@email.com";
        Map<String, Object> map = Map.ofEntries(Map.entry("email", "email"));
        when(userController.updateFields(eq(email), eq(map)))
                .thenThrow(new IllegalEmailFormatException("Illegal email format"));

        mockMvc.perform(put("/users/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(map)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Illegal email format"));
    }

    @Test
    public void invalidDateRangeExceptionTest() throws Exception {
        DateRange dateRange = new DateRange(new Date(Calendar.getInstance().getTimeInMillis()), null);
        when(userController.searchUsersByBirthDateRange(eq(dateRange)))
                .thenThrow(new InvalidDateRangeException("Field date 'to' is null"));

        mockMvc.perform(get("/users/searchByDate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dateRange)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Field date 'to' is null"));
    }

    @Test
    public void invalidBirthDateExceptionTest() throws Exception {
        Date birthDate = new Date(Calendar.getInstance().getTimeInMillis());
        User user = new User();
        user.setEmail("email@email.com");
        user.setFirstname("asd");
        user.setLastname("asd");
        user.setBirthDate(birthDate);

        when(userController.create(eq(user)))
                .thenThrow(new InvalidBirthDateException("Invalid birth date " + birthDate));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid birth date " + birthDate));
    }

    @Test
    public void httpRequestMethodNotSupportedExceptionTest() throws Exception {
        mockMvc.perform(get("/users/{message}", "hi"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.message").value("Request method 'GET' is not supported"));
    }

    @Test
    public void httpMessageNotReadableExceptionTest() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void methodArgumentNotValidExceptionTest() throws Exception {
        mockMvc.perform(put("/users/" + "email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

}
