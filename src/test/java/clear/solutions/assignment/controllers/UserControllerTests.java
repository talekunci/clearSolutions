package clear.solutions.assignment.controllers;

import clear.solutions.assignment.entities.User;
import clear.solutions.assignment.repositories.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService service;

    @Test
    public void getTest() throws Exception {
        List<User> mockUsers = List.of(
                new User("email", "name", "laname", new Date(1L), "home", "123456789"),
                new User("email1", "name1", "laname1", new Date(1L), "home1", "123456789")
        );
        when(service.getAll()).thenReturn(mockUsers);

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("email"))
                .andExpect(jsonPath("$[0].firstname").value("name"))
                .andExpect(jsonPath("$[1].email").value("email1"))
                .andExpect(jsonPath("$[1].firstname").value("name1"));

    }

    @Test
    public void createTest() throws Exception {
        var mockUser = new User("email@email.com", "name", "laname", new Date(1L), "home", "123456789");
        when(service.create(Mockito.any(User.class))).thenReturn(mockUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value("email@email.com"))
                .andExpect(jsonPath("firstname").value("name"));
    }

    @Test
    public void updateTest() throws Exception {
        String email = "email@email.com";
        var mockUser = new User(email, "name", "laname", new Date(1L), "home", "12345");
        var updatedUser = new User(email, "josh", "ross", new Date(1L), "home", "12345");

        when(service.update(eq(email), Mockito.any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value("email@email.com"))
                .andExpect(jsonPath("firstname").value("josh"))
                .andExpect(jsonPath("lastname").value("ross"));
    }

    @Test
    public void deleteTest() throws Exception {
        String email = "email@email.com";

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + email))
                        .andExpect(status().isOk());

        verify(service).delete(email);
    }
}
