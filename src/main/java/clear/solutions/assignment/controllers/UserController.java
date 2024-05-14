package clear.solutions.assignment.controllers;

import clear.solutions.assignment.entities.User;
import clear.solutions.assignment.repositories.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @PostMapping
    public User create(@Valid @RequestBody User dto) {
        return userService.create(dto);
    }

    @PutMapping("/{email}")
    public User update(@PathVariable String email, @Valid @RequestBody User dto) {
        return userService.update(email, dto);
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String email) {
        userService.delete(email);
    }
}
