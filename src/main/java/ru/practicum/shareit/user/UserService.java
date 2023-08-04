package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {
    UserDto save(UserDto userDto);

    List<UserDto> findAll();

    UserDto findDtoById(long userId);

    User findById(long userId);

    UserDto update(UserDto userDto, long userId);

    void delete(long userId);
}