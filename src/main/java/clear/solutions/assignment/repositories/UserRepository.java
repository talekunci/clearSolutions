package clear.solutions.assignment.repositories;

import clear.solutions.assignment.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
