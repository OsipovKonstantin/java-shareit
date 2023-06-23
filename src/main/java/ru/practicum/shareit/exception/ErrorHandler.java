package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(UserNotFoundException e) {
        log.warn("{}", e.getMessage());
        return new ErrorResponse("Пользователь не найден.", e.getMessage());
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleItemNotFoundException(ItemNotFoundException e) {
        log.warn("{}", e.getMessage());
        return new ErrorResponse("Предмет не найден.", e.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleEmailAlreadyExistException(EmailAlreadyExistException e) {
        log.warn("{}", e.getMessage());
        return new ErrorResponse("Еmail уже существует.", e.getMessage());
    }

    @ExceptionHandler(IncompatibleItemIdException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleIncompatibleItemIdException(IncompatibleItemIdException e) {
        log.warn("{}", e.getMessage());
        return new ErrorResponse("id предметов не совпадают.", e.getMessage());
    }

    @ExceptionHandler(IncompatibleUserIdException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleIncompatibleUserIdException(IncompatibleUserIdException e) {
        log.warn("{}", e.getMessage());
        return new ErrorResponse("id пользователей не совпадают.", e.getMessage());
    }
}
