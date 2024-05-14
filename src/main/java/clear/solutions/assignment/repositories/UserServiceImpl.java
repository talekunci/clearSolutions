package clear.solutions.assignment.repositories;

import clear.solutions.assignment.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findById(email);
    }

    @Override
    public User create(User dto) {
        User save = userRepository.save(dto);

        logger.info("User {} has been created", save);

        return save;
    }

    @Override
    public User update(String email, User dto) {
        Optional<User> optionalUser = userRepository.findById(email)
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

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userRepository.save(user);
            logger.info("User {} has been updated", user.getEmail());
            return user;
        }

        return null;
    }

    @Override
    public void delete(String email) {
        userRepository.deleteById(email);
        logger.info("User {} has been deleted", email);
    }
}
