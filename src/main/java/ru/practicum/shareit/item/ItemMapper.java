package ru.practicum.shareit.item;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.booking.dto.BookingDtoForGetItemResponse;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@UtilityClass
public class ItemMapper {
    public static CreateItemResponse toCreateItemResponse(Item item) {
        return new CreateItemResponse(item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable());
    }

    public static ItemDtoForGetBookingDto toItemDtoForBooking(Item item) {
        return new ItemDtoForGetBookingDto(item.getId(), item.getName());
    }

    public static GetItemResponse toGetItemResponse(Item item,
                                                    BookingDtoForGetItemResponse lastBooking,
                                                    BookingDtoForGetItemResponse nextBooking,
                                                    List<CommentResponse> commentResponse) {
        return new GetItemResponse(item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                lastBooking,
                nextBooking,
                commentResponse);
    }

    public static Item toItem(User user, CreateItemRequest createItemRequest) {
        return new Item()
                .setName(createItemRequest.getName())
                .setDescription(createItemRequest.getDescription())
                .setAvailable(createItemRequest.getAvailable())
                .setOwner(user);
    }
}
