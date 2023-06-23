package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.IncompatibleUserIdException;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();

    @Override
    public Item save(Item item) {
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item update(Item item, Item oldItem) {
        Item updatedItem = Item.builder()
                .id(item.getId())
                .name(item.getName() == null ? oldItem.getName() : item.getName())
                .description(item.getDescription() == null ? oldItem.getDescription() : item.getDescription())
                .available(item.getAvailable() == null ? oldItem.getAvailable() : item.getAvailable())
                .owner(oldItem.getOwner())
                .request(oldItem.getRequest())
                .build();
        items.put(item.getId(), updatedItem);
        return updatedItem;
    }

    @Override
    public Item get(long userId, long itemId) {
        return items.get(itemId);
    }

    @Override
    public List<Item> getAll(long userId) {
        return items.values().stream().filter(i -> i.getOwner().getId() == userId).collect(Collectors.toList());
    }

    @Override
    public List<Item> search(String text) {
        return items.values().stream()
                .filter(i -> (i.getName().toLowerCase().contains(text.toLowerCase())
                        || i.getDescription().toLowerCase().contains(text.toLowerCase()))
                        && i.getAvailable())
                .collect(Collectors.toList());
    }
}
