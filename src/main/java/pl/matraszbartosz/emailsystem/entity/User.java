package pl.matraszbartosz.emailsystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @NotNull
    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @CreationTimestamp
    @Column(name = "date_created")
    private LocalDate dateCreated;

    @UpdateTimestamp
    @Column(name = "last_update")
    private LocalDate lastUpdate;

    @JsonManagedReference("email")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Email> userEmails;

    public void addEmail(Email email) {
        if (email != null) {
            if (userEmails == null) {
                userEmails = new ArrayList<>();
            }
            userEmails.add(email);
            email.setUser(this);
        }
    }
}
