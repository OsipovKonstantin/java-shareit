package ru.practicum.shareit.user;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserRepository {

    User save(User user);

    List<User> findAll();

    User find(long userId);

    User update(long userId, User user);

    void delete(long userId);
}
