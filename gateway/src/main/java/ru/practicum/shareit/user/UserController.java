package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.CreateUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;

import javax.validation.Valid;

@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserClient userClient;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid CreateUserRequest createUserRequest) {
        log.info("Post user {}", createUserRequest);
        return userClient.save(createUserRequest);
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        log.info("Get all users");
        return userClient.findAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> findDtoById(@PathVariable long userId) {
        log.info("Get user with userId={}", userId);
        return userClient.findDtoById(userId);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Object> update(@RequestBody @Valid UpdateUserRequest updateUserRequest,
                                         @PathVariable long userId) {
        log.info("Patch user {}, userId={}", updateUserRequest, userId);
        return userClient.update(updateUserRequest, userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> delete(@PathVariable long userId) {
        log.info("Delete user with userId={}", userId);
        return userClient.delete(userId);
    }
}