package pl.matraszbartosz.emailsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matraszbartosz.emailsystem.entity.User;
import pl.matraszbartosz.emailsystem.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public Optional<User> getUserById(long id) {
        return this.userRepository.findById(id);
    }

    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return this.userRepository.getUserByEmail(email);
    }

}
