package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto save(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        return UserMapper.toUserDto(userRepository.save(user));
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public UserDto findDtoById(long userId) {
        return UserMapper.toUserDto(findById(userId));
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public User findById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("Пользователь с id %s не найден.", userId)));
    }

    @Override
    public UserDto update(UserDto userDto, long userId) {
        User user = UserMapper.toUser(userDto.setId(userId));
        User oldUser = findById(userId);
        return UserMapper.toUserDto(userRepository.save(oldUser
                .setName(user.getName() == null ? oldUser.getName() : user.getName())
                .setEmail(user.getEmail() == null ? oldUser.getEmail() : user.getEmail())));
    }

    @Override
    public void delete(long userId) {
        findById(userId);
        userRepository.deleteById(userId);
    }
}