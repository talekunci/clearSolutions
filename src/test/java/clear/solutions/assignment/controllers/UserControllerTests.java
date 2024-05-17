package clear.solutions.assignment.controllers;

import clear.solutions.assignment.entities.DateRange;
import clear.solutions.assignment.entities.User;
import clear.solutions.assignment.repositories.UserRepository;
import clear.solutions.assignment.repositories.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
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
    @Autowired
    private Repositories repositories;
    @Autowired
    private UserRepository userRepository;

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
    public void searchUsersByBirthDateRangeTest() throws Exception {
        Date from = new Date(1L);
        Date to = new Date(10000000000L);
        DateRange range = new DateRange(from, to);

        List<User> mockUsers = List.of(
                new User("email", "name", "laname", new Date(5000000L), "home", "123456789"),
                new User("email1", "name1", "laname1", new Date(5000000L), "home1", "123456789")
        );
        when(service.searchByDate(any(Date.class), any(Date.class))).thenReturn(mockUsers);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/searchByDate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(range)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("email"))
                .andExpect(jsonPath("$[0].firstname").value("name"))
                .andExpect(jsonPath("$[1].email").value("email1"))
                .andExpect(jsonPath("$[1].firstname").value("name1"));

    }

    @Test
    public void createTest() throws Exception {
        var mockUser = new User("email@email.com", "name", "laname", new Date(1L), "home", "123456789");
        when(service.create(any(User.class))).thenReturn(mockUser);

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

        when(service.update(eq(email), any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value("email@email.com"))
                .andExpect(jsonPath("firstname").value("josh"))
                .andExpect(jsonPath("lastname").value("ross"));
    }

    @Test
    public void updateFields() throws Exception {
        String email = "email@email.com";
        var mockUser = new User(email, "name", "laname", new Date(1L), "home", "12345");
        var updatedUser = new User(email, "josh", "ross", new Date(1L), "home", "12345");
        Map<String, Object> entries = Map.ofEntries(
                Map.entry("firstname", "josh"),
                Map.entry("lastname", "ross")
        );

        when(service.update(eq(email), eq(entries))).thenReturn(updatedUser);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(entries)))
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
