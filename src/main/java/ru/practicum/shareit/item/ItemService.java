package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item add(long userId, ItemDto itemDto);

    Item update(long userId, long itemId, ItemDto itemDto);

    Item get(long itemId);

    List<Item> getAll(long userId);

    List<Item> search(String text);
}