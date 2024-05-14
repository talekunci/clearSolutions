package clear.solutions.assignment.controllers;

import clear.solutions.assignment.entities.DateRange;
import clear.solutions.assignment.entities.User;
import clear.solutions.assignment.repositories.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> getAll() {
        return service.getAll();
    }

    @GetMapping("/searchByDate")
    public List<User> searchUsersByBirthDateRange(@Valid @RequestBody DateRange range) {
        return service.searchByDate(range.getFrom(), range.getTo());
    }

    @PostMapping
    public User create(@Valid @RequestBody User dto) {
        return service.create(dto);
    }

    @PutMapping("/{email}")
    public User update(@PathVariable String email, @Valid @RequestBody User dto) {
        return service.update(email, dto);
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String email) {
        service.delete(email);
    }
}
