package ru.practicum.shareit.item;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.dto.BookingShort;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ItemMapper {
    public ItemResponse toItemResponse(Item item) {
        return new ItemResponse(item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getRequest() == null ? null : item.getRequest().getId());
    }

    public ItemShort toItemShort(Item item) {
        return new ItemShort(item.getId(), item.getName());
    }

    public GetItemResponse toGetItemResponse(Item item,
                                             BookingShort lastBooking,
                                             BookingShort nextBooking,
                                             List<CommentResponse> commentResponse) {
        return new GetItemResponse(item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                lastBooking,
                nextBooking,
                commentResponse);
    }

    public GetItemResponse toGetItemResponse(Item item) {
        Booking lastBookingNotDto = item.getBookings().stream().filter(b -> b.getStart().isBefore(LocalDateTime.now()))
                .max(Comparator.comparing(Booking::getStart)).orElse(null);
        Booking nextBookingNotDto = item.getBookings().stream().filter(b -> b.getStart().isAfter(LocalDateTime.now()))
                .min(Comparator.comparing(Booking::getStart)).orElse(null);
        return new GetItemResponse(item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                lastBookingNotDto == null ? null : BookingMapper.toBookingShort(lastBookingNotDto),
                nextBookingNotDto == null ? null : BookingMapper.toBookingShort(nextBookingNotDto),
                item.getComments().stream().map(CommentMapper::toCommentResponse).collect(Collectors.toList()));
    }

    public Item toItem(User user, CreateItemRequest createItemRequest) {
        return new Item()
                .setName(createItemRequest.getName())
                .setDescription(createItemRequest.getDescription())
                .setAvailable(createItemRequest.getAvailable())
                .setOwner(user);
    }
}
