package pl.matraszbartosz.emailsystem.dto;

import pl.matraszbartosz.emailsystem.entity.Email;

public class EmailDTOMapper {

    private EmailDTOMapper() {
    }

    public static Email returnEmail(EmailDTO emailDTO) {
        Email email = new Email();
        email.setEmailTo(emailDTO.getEmailTo());
        email.setContent(emailDTO.getContent());
        return email;
    }
}
