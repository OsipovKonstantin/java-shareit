package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.EmailAlreadyExistException;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User save(User user) {
        if (users.values().stream().anyMatch(u -> u.getEmail().equals(user.getEmail())))
            throw new EmailAlreadyExistException(String.format("email %s уже существует.", user.getEmail()));
        user.setId(GeneratorUserId.getUserId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User find(long userId) {
        return users.get(userId);
    }

    @Override
    public User update(long userId, User user) {
        if (users.values().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()) && u.getId() != userId))
            throw new EmailAlreadyExistException(String.format("email %s уже существует.", user.getEmail()));
        User updatedUser = User.builder()
                .id(userId)
                .email(user.getEmail() == null ? users.get(userId).getEmail() : user.getEmail())
                .name(user.getName() == null ? users.get(userId).getName() : user.getName())
                .build();
        users.put(userId, updatedUser);
        return updatedUser;
    }

    @Override
    public void delete(long userId) {
        users.remove(userId);
    }
}
