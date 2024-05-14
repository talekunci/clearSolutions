package clear.solutions.assignment.repositories;

import clear.solutions.assignment.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    List<User> findUsersByBirthDateBetween(Date from, Date to);

}
