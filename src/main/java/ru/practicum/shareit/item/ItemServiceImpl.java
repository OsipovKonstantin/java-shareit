package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.IncompatibleItemIdException;
import ru.practicum.shareit.exception.IncompatibleUserIdException;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final UserService userService;
    private final ItemRepository itemRepository;

    @Override
    public Item add(long userId, ItemDto itemDto) {
        User user = userService.get(userId);

        Item item = ItemMapper.toItem(user, itemDto);
        item.setId(GeneratorItemId.getId());
        return itemRepository.save(item);
    }

    @Override
    public Item update(long userId, long itemId, ItemDto itemDto) {
        User user = userService.get(userId);

        if (itemDto.getId() == null)
            itemDto.setId(itemId);
        if (itemId != itemDto.getId())
            throw new IncompatibleItemIdException(String.format("id предметов %s и %s не совпадают.",
                    itemId, itemDto.getId()));
        Item item = ItemMapper.toItem(user, itemDto);
        Item oldItem = get(itemId);
        if (!Objects.equals(item.getOwner().getId(), oldItem.getOwner().getId()))
            throw new IncompatibleUserIdException("id пользователей не совпадают.");
        return itemRepository.update(item, oldItem);
    }

    @Override
    public Item get(long itemId) {
        Item item = itemRepository.get(itemId);
        if (item == null)
            throw new ItemNotFoundException(String.format("Предмет с id %s не найден.", itemId));
        return item;
    }

    @Override
    public List<Item> getAll(long userId) {
        return itemRepository.getAll(userId);
    }

    @Override
    public List<Item> search(String text) {
        if (text.isBlank())
            return new ArrayList<>();
        return itemRepository.search(text);
    }
}
