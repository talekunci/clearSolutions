package clear.solutions.assignment.repositories;

import clear.solutions.assignment.entities.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceImplTests {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    public void getAllUsersTest() {
        List<User> mockUsers = List.of(
                new User("email", "name", "laname", new Date(1L), "home", "123456789"),
                new User("email1", "name1", "laname1", new Date(1L), "home1", "123456789"),
                new User("email2", "name2", "laname2", new Date(1L), "home2", "123456789")
        );
        when(repository.findAll()).thenReturn(mockUsers);

        List<User> actual = service.getAll();

        assertEquals(mockUsers, actual);
    }

    @Test
    public void getByEmailTest() {
        String email = "email@email.com";
        var mockUser = new User();
        mockUser.setEmail(email);

        when(repository.findById(email)).thenReturn(Optional.of(mockUser));

        Optional<User> actual = service.getByEmail(email);

        assertEquals(mockUser, actual.get());
    }

    @Test
    public void searchByDateTest() {
        List<User> mockUsers = List.of(
                new User("email", "name", "laname", new Date(1L), "home", "123456789"),
                new User("email1", "name1", "laname1", new Date(1L), "home1", "123456789"),
                new User("email2", "name2", "laname2", new Date(1L), "home2", "123456789")
        );

        when(repository.findUsersByBirthDateBetween(any(Date.class), any(Date.class)))
                .thenReturn(mockUsers);

        assertEquals(service.searchByDate(new Date(1L), new Date(1L)), mockUsers);
    }

    @Test
    public void createTest() {
        var mockUser = new User("email@email.com", "name", "laname", new Date(1L), "home", "12345");
        var savedUser = new User("email@email.com", "name", "laname", new Date(1L), "home", "12345");

        when(repository.save(mockUser)).thenReturn(savedUser);

        assertEquals(service.create(mockUser), savedUser);
    }

    @Test
    public void updateTest() {
        String email = "email@email.com";
        var mockUser = new User(email, "name", "laname", new Date(1L), "home", "12345");
        var updatedUser = new User(email, "josh", "ross", new Date(1L), "home", "12345");

        when(repository.findById(email)).thenReturn(Optional.of(mockUser));

        assertEquals(service.update(email, updatedUser), updatedUser);
    }

    @Test
    public void deleteTest() {
        String email = "email@email.com";

        service.delete(email);

        verify(repository).deleteById(email);
    }
}
