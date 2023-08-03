package ru.practicum.shareit.item;

import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.item.dto.CommentResponse;
import ru.practicum.shareit.item.dto.CreateCommentRequest;
import ru.practicum.shareit.item.dto.GetItemResponse;
import ru.practicum.shareit.item.dto.ItemResponse;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item saveItem(Item item, Long requestId);

    Item update(Item item);

    Item findById(long itemId);

    GetItemResponse findDtoById(long itemId, long ownerId);

    List<GetItemResponse> findByOwnerId(long userId, Pageable page);

    List<ItemResponse> searchAvailableItemsByText(String text, Pageable page);

    CommentResponse saveComment(Long authorId, Long itemId, CreateCommentRequest createCommentRequest);
}