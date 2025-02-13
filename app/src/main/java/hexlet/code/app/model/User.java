package hexlet.code.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String firstName;
    String lastName;
    String email;
    String password;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
