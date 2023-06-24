package ru.practicum.shareit.user;

public class GeneratorUserId {
    private static long userId = 1;

    public static long getUserId() {
        return userId++;
    }
}
