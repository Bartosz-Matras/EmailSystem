package pl.matraszbartosz.emailsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
}
