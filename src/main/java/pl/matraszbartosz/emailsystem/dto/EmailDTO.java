package pl.matraszbartosz.emailsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDTO {
    private String emailTo;
    private String emailFrom;
    private String content;
}
