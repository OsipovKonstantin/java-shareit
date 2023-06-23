package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User add(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User get(long userId) {
        User user = userRepository.find(userId);
        if(user == null)
            throw new UserNotFoundException(String.format("Пользователь с id %s не найден.", userId));
        return user;
    }

    @Override
    public User update(long userId, UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        get(userId);
        return userRepository.update(userId, user);
    }

    @Override
    public void delete(long userId) {
        get(userId);
        userRepository.delete(userId);
    }
}
