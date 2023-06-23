package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {
    Item save(Item item);

    Item update(Item item, Item oldItem);

    Item get(long userId, long itemId);

    List<Item> getAll(long userId);

    List<Item> search(String text);
}
