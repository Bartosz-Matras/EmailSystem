package pl.matraszbartosz.emailsystem.dto;

import pl.matraszbartosz.emailsystem.entity.User;

public class UserDTOMapper {

    private UserDTOMapper() {
    }

    public static User returnUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return user;
    }
}
