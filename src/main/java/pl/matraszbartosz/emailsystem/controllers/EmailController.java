package pl.matraszbartosz.emailsystem.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.matraszbartosz.emailsystem.dto.EmailDTO;
import pl.matraszbartosz.emailsystem.entity.Email;
import pl.matraszbartosz.emailsystem.entity.User;
import pl.matraszbartosz.emailsystem.service.EmailService;
import pl.matraszbartosz.emailsystem.service.UserService;

import java.util.List;
import java.util.Optional;

import static pl.matraszbartosz.emailsystem.dto.EmailDTOMapper.*;

@RestController
@RequestMapping("/api/v1/emails")
@RequiredArgsConstructor
@Tag(name = "Emails")
public class EmailController {

    private final EmailService emailService;
    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get list of emails for user")
    @ApiResponse(responseCode = "200", description = "List of emails for user")
    public ResponseEntity<List<Email>> getEmails(@RequestParam("email") String email) {
        Optional<User> user = this.userService.getUserByEmail(email);
        return user.map(value -> ResponseEntity.ok(this.emailService.getEmailsForUser(value.getUserId())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get email by ID", description = "Returns a email as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Email.class))}),
            @ApiResponse(responseCode = "404", description = "Email not found",
                    content = {@Content(schema = @Schema(example = "Email not found"))})
    })
    public ResponseEntity<Email> getEmailById(@PathVariable long id) {
        Optional<Email> emailOptional = this.emailService.getEmailById(id);
        return emailOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Add email")
    @ApiResponse(responseCode = "201", description = "Email created")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<EmailDTO> saveEmail(@RequestBody EmailDTO emailDTO) {
        Optional<User> optionalUser = this.userService.getUserByEmail(emailDTO.getEmailFrom());
        if (optionalUser.isPresent()) {
            Email createdEmail = returnEmail(emailDTO);
            createdEmail.setUser(optionalUser.get());
            optionalUser.get().addEmail(createdEmail);
            this.emailService.saveEmail(createdEmail);
            return ResponseEntity.status(HttpStatus.CREATED).body(emailDTO);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> handleNumberFormatException() {
        return ResponseEntity.badRequest().body("Invalid ID format. Please provide a numeric ID.");
    }
}
