package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingDtoForGetItemResponse;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.exception.IncompatibleUserIdException;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.UserNotBookedBeforeException;
import ru.practicum.shareit.item.dto.CommentResponse;
import ru.practicum.shareit.item.dto.CreateCommentRequest;
import ru.practicum.shareit.item.dto.GetItemResponse;
import ru.practicum.shareit.item.dto.SearchItemResponse;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final CommentRepository commentRepository;

    @Override
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item update(Item item) {
        Item oldItem = findById(item.getId());
        if (!Objects.equals(item.getOwner().getId(), oldItem.getOwner().getId()))
            throw new IncompatibleUserIdException("id пользователей не совпадают.");

        return itemRepository.save(oldItem
                .setName(item.getName() == null ? oldItem.getName() : item.getName())
                .setDescription(item.getDescription() == null ? oldItem.getDescription() : item.getDescription())
                .setAvailable(item.getAvailable() == null ? oldItem.getAvailable() : item.getAvailable()));
    }

    @Override
    public Item findById(long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(String.format("Предмет с id %s не найден.", itemId)));
    }

    @Override
    public GetItemResponse findDtoById(long itemId, long ownerId) {
        Item item = findById(itemId);
        List<CommentResponse> comments = commentRepository.findByItem_IdOrderByCreatedAsc(itemId).stream()
                .map(CommentMapper::toCommentResponse).collect(Collectors.toList());
        if (!Objects.equals(item.getOwner().getId(), ownerId))
            return ItemMapper.toGetItemResponse(item, null, null, comments);

        BookingDtoForGetItemResponse lastBooking = bookingRepository
                .findFirstByItemIdAndStatusAndStartIsBeforeOrderByStartDesc(itemId, Status.APPROVED, LocalDateTime.now());
        BookingDtoForGetItemResponse nextBooking = bookingRepository
                .findFirstByItemIdAndStatusAndStartIsAfterOrderByStartAsc(itemId, Status.APPROVED, LocalDateTime.now());
        return ItemMapper.toGetItemResponse(item, lastBooking, nextBooking, comments);
    }

    @Override
    public List<GetItemResponse> findByOwnerId(long userId) {
        return itemRepository.findByOwnerId(userId).stream().map(i -> ItemMapper.toGetItemResponse(i,
                        bookingRepository.findFirstByItemIdAndStatusAndStartIsBeforeOrderByStartDesc(i.getId(),
                                Status.APPROVED, LocalDateTime.now()),
                        bookingRepository.findFirstByItemIdAndStatusAndStartIsAfterOrderByStartAsc(i.getId(),
                                Status.APPROVED, LocalDateTime.now()),
                        commentRepository.findByItem_IdOrderByCreatedAsc(i.getId()).stream()
                                .map(CommentMapper::toCommentResponse).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Override
    public List<SearchItemResponse> searchAvailableItemsByText(String text) {
        if (text.isBlank())
            return new ArrayList<>();
        return itemRepository.searchAvailableItemsByText(text);
    }

    @Override
    public CommentResponse saveComment(Long authorId, Long itemId, CreateCommentRequest createCommentRequest) {
        if (bookingRepository.findFirstByBooker_IdAndItem_IdAndStatusAndEndIsBefore(authorId, itemId, Status.APPROVED,
                LocalDateTime.now()) == null)
            throw new UserNotBookedBeforeException("Пользователь не может оставить отзыв, " +
                    "поскольку не пользовался вещью в прошлом.");
        return CommentMapper.toCommentResponse(commentRepository.save(new Comment()
                .setText(createCommentRequest.getText())
                .setItem(findById(itemId))
                .setAuthor(userService.findById(authorId))));
    }
}