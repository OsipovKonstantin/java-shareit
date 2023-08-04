package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.CreateBookingDto;
import ru.practicum.shareit.booking.dto.GetBookingDto;

import java.util.List;

public interface BookingService {
    GetBookingDto save(Long bookerId, CreateBookingDto createBookingDto);

    GetBookingDto update(Long bookingId, Long ownerId, boolean approved);

    GetBookingDto findByIdAndOwnerOrBookerId(Long bookingId, Long ownerOrBookerId);

    List<GetBookingDto> findByBookerIdAndState(Long bookerId, String state, Long from, int size);

    List<GetBookingDto> findByItemOwnerIdAndState(Long ownerId, String state, Long from, int size);
}
