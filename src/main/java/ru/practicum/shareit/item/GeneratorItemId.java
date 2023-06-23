package ru.practicum.shareit.item;

public class GeneratorItemId {
    private static long id = 1;

    public static long getId() {
        return id++;
    }
}
