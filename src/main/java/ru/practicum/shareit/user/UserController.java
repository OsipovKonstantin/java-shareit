package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.validation.CreateGroup;
import ru.practicum.shareit.validation.UpdateGroup;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserDto save(@RequestBody @Validated(CreateGroup.class) UserDto userDto) {
        return userService.save(userDto);
    }

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public UserDto findDtoById(@PathVariable long userId) {
        return userService.findDtoById(userId);
    }

    @PatchMapping("/{userId}")
    public UserDto update(@RequestBody @Validated(UpdateGroup.class) UserDto userDto, @PathVariable long userId) {
        return userService.update(userDto, userId);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable long userId) {
        userService.delete(userId);
    }
}
