package ru.practicum.shareit.booking.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class CreateBookingDto {
    @NotNull
    private Long itemId;

    @NotNull
    @Future
    private LocalDateTime start;

    @NotNull
    @Future
    private LocalDateTime end;
}
