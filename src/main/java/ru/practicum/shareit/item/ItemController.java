package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.user.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    private final UserService userService;

    @PostMapping
    public ItemResponse saveItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                 @RequestBody @Valid CreateItemRequest createItemRequest) {
        return itemService.saveItem(userId, createItemRequest);
    }

    @PatchMapping("/{itemId}")
    public ItemResponse update(@RequestHeader("X-Sharer-User-Id") long userId,
                               @PathVariable long itemId,
                               @RequestBody CreateItemRequest createItemRequest) {
        return itemService.update(userId, itemId, createItemRequest);
    }

    @GetMapping("/{itemId}")
    public GetItemResponse findById(@PathVariable long itemId,
                                    @RequestHeader("X-Sharer-User-Id") Long ownerId) {
        return itemService.findDtoById(itemId, ownerId);
    }

    @GetMapping
    public List<GetItemResponse> findByOwnerId(@RequestHeader("X-Sharer-User-Id") long userId,
                                               @RequestParam(defaultValue = "0") @Min(0) Long from,
                                               @RequestParam(defaultValue = "10") @Min(1) int size) {
        return itemService.findByOwnerId(userId, from, size);
    }

    @GetMapping("/search")
    public List<ItemResponse> searchAvailableItemsByText(@RequestParam String text,
                                                         @RequestParam(defaultValue = "0") @Min(0) Long from,
                                                         @RequestParam(defaultValue = "10") @Min(1) int size) {
        return itemService.searchAvailableItemsByText(text, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public CommentResponse saveComment(@RequestHeader("X-Sharer-User-Id") Long authorId, @PathVariable Long itemId,
                                       @RequestBody @Valid CreateCommentRequest createCommentRequest) {
        return itemService.saveComment(authorId, itemId, createCommentRequest);
    }
}