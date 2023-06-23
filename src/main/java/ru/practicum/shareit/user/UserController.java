package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserDto add(@RequestBody @Valid UserDto userDto) {
        return UserMapper.toUserDto(userService.add(userDto));
    }

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll().stream().map(u -> UserMapper.toUserDto(u)).collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public UserDto get(@PathVariable long userId) {
        return UserMapper.toUserDto(userService.get(userId));
    }

    @PatchMapping("/{userId}")
    public UserDto update(@RequestBody UserDto userDto, @PathVariable long userId) {
        return UserMapper.toUserDto(userService.update(userId, userDto));
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable long userId) {
        userService.delete(userId);
    }
}
