package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.item.dto.ItemShort;
import ru.practicum.shareit.user.dto.UserShort;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GetBookingDto {
    private long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Status status;
    private UserShort booker;
    private ItemShort item;
}