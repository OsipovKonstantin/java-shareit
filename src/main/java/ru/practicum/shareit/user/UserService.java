package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {
    User add(UserDto userDto);

    List<User> getAll();

    User get(long userId);

    User update(long userId, UserDto userDto);

    void delete(long userId);
}