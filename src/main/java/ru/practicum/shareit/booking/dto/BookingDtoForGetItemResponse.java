package ru.practicum.shareit.booking.dto;

import org.springframework.beans.factory.annotation.Value;

public interface BookingDtoForGetItemResponse {
    long getId();

    @Value("#{target.getBooker.getId}")
    long getBookerId();
}
