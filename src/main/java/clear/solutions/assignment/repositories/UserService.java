package clear.solutions.assignment.repositories;

import clear.solutions.assignment.entities.User;
import jakarta.transaction.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAll();

    Optional<User> getByEmail(String email);

    List<User> searchByDate(Date from, Date to);

    @Transactional
    User create(User dto);

    @Transactional
    User update(String email, User dto);

    void delete(String email);

}
