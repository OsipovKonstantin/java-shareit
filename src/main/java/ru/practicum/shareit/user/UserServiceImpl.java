package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("Пользователь с id %s не найден.", userId)));
    }

    @Override
    public User update(User user) {
        User oldUser = findById(user.getId());

        return userRepository.save(oldUser
                .setName(user.getName() == null ? oldUser.getName() : user.getName())
                .setEmail(user.getEmail() == null ? oldUser.getEmail() : user.getEmail())
        );
    }

    @Override
    public void delete(long userId) {
        findById(userId);
        userRepository.deleteById(userId);
    }
}