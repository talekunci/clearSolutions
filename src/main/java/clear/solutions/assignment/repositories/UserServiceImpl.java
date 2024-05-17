package clear.solutions.assignment.repositories;

import clear.solutions.assignment.controllers.exceptions.IllegalEmailFormatException;
import clear.solutions.assignment.entities.User;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final Pattern emailPattern;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;

        emailPattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return repository.findById(email);
    }

    @Override
    public List<User> searchByDate(Date from, Date to) {
        return repository.findUsersByBirthDateBetween(from, to);
    }

    @Override
    public User create(User dto) {
        User save = repository.save(dto);

        logger.info("User {} has been created", save);

        return save;
    }

    @Override
    public User update(String email, User dto) {
        Optional<User> optionalUser = repository.findById(email)
                .map(user -> {
                    if (StringUtils.hasText(dto.getEmail())) {
                        user.setEmail(dto.getEmail());
                    }

                    if (StringUtils.hasText(dto.getFirstname())) {
                        user.setFirstname(dto.getFirstname());
                    }

                    if (StringUtils.hasText(dto.getLastname())) {
                        user.setLastname(dto.getLastname());
                    }

                    if (dto.getBirthDate() != null) {
                        user.setBirthDate(dto.getBirthDate());
                    }

                    if (StringUtils.hasText(dto.getAddress())) {
                        user.setAddress(dto.getAddress());
                    }

                    if (StringUtils.hasText(dto.getPhoneNumber())) {
                        user.setPhoneNumber(dto.getPhoneNumber());
                    }

                    return user;
                });


        if (optionalUser.isEmpty()) {
            logger.info("User {} does not exist", email);
            throw new NullPointerException("User " + email + " does not exist");
        }

        User user = optionalUser.get();
        repository.save(user);
        logger.info("User {} has been updated", user.getEmail());
        return user;
    }

    @Transactional
    public User update(String email, Map<String, Object> body) {
        Optional<User> byId = repository.findById(email);

        if (byId.isEmpty()) {
            logger.info("User {} does not exist", email);
            throw new NullPointerException("User " + email + " does not exist");
        }

        User user = byId.get();

        int count = 0;

        for (Map.Entry<String, Object> entry : body.entrySet()) {
            if (!StringUtils.hasText(entry.getValue().toString())) continue;

            switch (entry.getKey()) {
                case "email":
                    if (emailPattern.matcher(entry.getValue().toString()).matches()) {
                        user.setEmail(entry.getValue().toString());
                    } else {
                        logger.info("User {} - new email {} doesn't match the pattern", email, entry.getValue());
                        throw new IllegalEmailFormatException("Illegal email format");
                    }
                    break;
                case "firstname":
                    user.setFirstname(entry.getValue().toString());
                    break;
                case "lastname":
                    user.setLastname(entry.getValue().toString());
                    break;
                case "birthDate":
                    user.setBirthDate(Date.valueOf(entry.getValue().toString()));
                    break;
                case "address":
                    user.setAddress(entry.getValue().toString());
                    break;
                case "phoneNumber":
                    user.setPhoneNumber(entry.getValue().toString());
                    break;
            }

            count++;
        }

        if (count > 0) {
            logger.info("User {} has been updated", user.getEmail());
        }

        return user;
    }

    @Override
    public void delete(String email) {
        repository.deleteById(email);
        logger.info("User {} has been deleted", email);
    }
}
