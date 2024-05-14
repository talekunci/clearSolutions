package clear.solutions.assignment.entities;

import clear.solutions.assignment.validation.BirthDateValidation;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @Nullable
    @Column(name = "email", length = 50, unique = true, nullable = false)
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Nullable
    @Column(name = "firstname", length = 50, nullable = false)
    private String firstname;

    @Nullable
    @Column(name = "lastname", length = 50, nullable = false)
    private String lastname;

    @Nullable
    @BirthDateValidation
    @Column(name = "birthdate", nullable = false)
    private Date birthDate;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(email, user.email) && Objects.equals(firstname, user.firstname) && Objects.equals(lastname, user.lastname) && Objects.equals(birthDate, user.birthDate) && Objects.equals(address, user.address) && Objects.equals(phoneNumber, user.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, firstname, lastname, birthDate, address, phoneNumber);
    }
}
