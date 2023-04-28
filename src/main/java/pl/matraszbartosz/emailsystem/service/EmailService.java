package pl.matraszbartosz.emailsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matraszbartosz.emailsystem.entity.Email;
import pl.matraszbartosz.emailsystem.repository.EmailRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepository emailRepository;

    public List<Email> getEmailsForUser(long id) {
        return this.emailRepository.findEmailByUserId(id);
    }

    public Optional<Email> getEmailById(long id) {
        return this.emailRepository.findById(id);
    }

    public Email saveEmail(Email email) {
        return this.emailRepository.save(email);
    }

}
