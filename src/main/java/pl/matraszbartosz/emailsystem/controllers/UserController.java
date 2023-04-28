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
import pl.matraszbartosz.emailsystem.dto.UserDTO;
import pl.matraszbartosz.emailsystem.dto.UserDTOMapper;
import pl.matraszbartosz.emailsystem.entity.User;
import pl.matraszbartosz.emailsystem.service.UserService;

import java.util.List;
import java.util.Optional;

import static pl.matraszbartosz.emailsystem.dto.UserDTOMapper.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get list of users")
    @ApiResponse(responseCode = "200", description = "List of users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(this.userService.getUsers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Returns a user as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(schema = @Schema(example = "User not found"))})
    })
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        Optional<User> optionalUser = this.userService.getUserById(id);
        return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Add user")
    public ResponseEntity<User> saveUser(@RequestBody UserDTO userDTO) {
        User savedUser = this.userService.saveUser(returnUser(userDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> handleNumberFormatException(NumberFormatException ex) {
        return ResponseEntity.badRequest().body("Invalid ID format. Please provide a numeric ID.");
    }

}
