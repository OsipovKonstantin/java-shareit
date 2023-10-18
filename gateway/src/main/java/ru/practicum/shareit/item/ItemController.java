package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CreateCommentRequest;
import ru.practicum.shareit.item.dto.CreateItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collections;

import static ru.practicum.shareit.util.Constants.USER_ID_HEADER;

@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> saveItem(@RequestHeader(USER_ID_HEADER) long ownerId,
                                           @RequestBody @Valid CreateItemRequest createItemRequest) {
        log.info("Create item {}, ownerId={}", createItemRequest, ownerId);
        return itemClient.saveItem(ownerId, createItemRequest);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@RequestHeader(USER_ID_HEADER) long ownerId,
                                         @PathVariable long itemId,
                                         @RequestBody @Valid UpdateItemRequest updateItemRequest) {
        log.info("Patch item {}, ownerId={}, itemId={}", updateItemRequest, ownerId, itemId);
        return itemClient.update(ownerId, itemId, updateItemRequest);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> findById(@PathVariable long itemId,
                                           @RequestHeader(USER_ID_HEADER) Long ownerId) {
        log.info("Get item with ownerId={}, itemId={}", ownerId, itemId);
        return itemClient.findDtoById(itemId, ownerId);
    }

    @GetMapping
    public ResponseEntity<Object> findByOwnerId(@RequestHeader(USER_ID_HEADER) long ownerId,
                                                @RequestParam(defaultValue = "0") @Min(0) @Max(Long.MAX_VALUE) Long from,
                                                @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        log.info("Get item with ownerId={}, from={}, size={}", ownerId, from, size);
        return itemClient.findByOwnerId(ownerId, from, size);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchAvailableItemsByText(@RequestParam String text,
                                                             @RequestParam(defaultValue = "0") @Min(0) @Max(Long.MAX_VALUE) Long from,
                                                             @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        log.info("Get item with text={}, from={}, size={}", text, from, size);
        if (text.isBlank())
            return ResponseEntity.ok(Collections.emptyList());
        return itemClient.searchAvailableItemsByText(text, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> saveComment(@RequestHeader(USER_ID_HEADER) Long authorId, @PathVariable Long itemId,
                                              @RequestBody @Valid CreateCommentRequest createCommentRequest) {
        log.info("Post comment {}, authorId={}, itemId={}", createCommentRequest, authorId, itemId);
        return itemClient.saveComment(authorId, itemId, createCommentRequest);
    }
}