package ru.practicum.shareit.item.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.dto.BookingShort;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.user.entity.User;

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
                                             List<CommentResponse> comments) {
        return new GetItemResponse(item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                lastBooking,
                nextBooking,
                comments);
    }

    public GetItemResponse toGetItemResponse(Item item) {
        Booking lastBookingNotDto = null;
        Booking nextBookingNotDto = null;

        if (item.getBookings() != null) {
            lastBookingNotDto = item.getBookings().stream().filter(b -> b.getStart().isBefore(LocalDateTime.now()))
                    .max(Comparator.comparing(Booking::getStart)).orElse(null);
            nextBookingNotDto = item.getBookings().stream().filter(b -> b.getStart().isAfter(LocalDateTime.now()))
                    .min(Comparator.comparing(Booking::getStart)).orElse(null);
        }
        return new GetItemResponse(item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                lastBookingNotDto == null ? null : BookingMapper.toBookingShort(lastBookingNotDto),
                nextBookingNotDto == null ? null : BookingMapper.toBookingShort(nextBookingNotDto),
                item.getComments() == null ? null :
                        item.getComments().stream().map(CommentMapper::toCommentResponse).collect(Collectors.toList()));
    }

    public Item toItem(User user, CreateItemRequest createItemRequest) {
        return new Item()
                .setName(createItemRequest.getName())
                .setDescription(createItemRequest.getDescription())
                .setAvailable(createItemRequest.getAvailable())
                .setOwner(user);
    }

    public Item toItem(User user, UpdateItemRequest updateItemRequest) {
        return new Item()
                .setName(updateItemRequest.getName())
                .setDescription(updateItemRequest.getDescription())
                .setAvailable(updateItemRequest.getAvailable())
                .setOwner(user);
    }
}
