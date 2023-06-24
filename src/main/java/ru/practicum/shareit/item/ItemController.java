package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto add(@RequestHeader("X-Sharer-User-Id") long userId, @RequestBody @Valid ItemDto itemDto) {
        return ItemMapper.toItemDto(itemService.add(userId, itemDto));
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") long userId,
                          @PathVariable long itemId,
                          @RequestBody ItemDto itemDto) {
        return ItemMapper.toItemDto(itemService.update(userId, itemId, itemDto));
    }

    @GetMapping("/{itemId}")
    public ItemDto get(@PathVariable long itemId) {
        return ItemMapper.toItemDto(itemService.get(itemId));
    }

    @GetMapping
    public List<ItemDto> getAll(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.getAll(userId).stream().map(i -> ItemMapper.toItemDto(i)).collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam String text) {
        return itemService.search(text).stream().map(i -> ItemMapper.toItemDto(i)).collect(Collectors.toList());
    }
}