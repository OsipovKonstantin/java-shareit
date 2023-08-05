package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingResponse;
import ru.practicum.shareit.booking.dto.CreateBookingRequest;

import java.util.List;

public interface BookingService {
    BookingResponse save(Long bookerId, CreateBookingRequest createBookingRequest);

    BookingResponse update(Long bookingId, Long ownerId, boolean approved);

    BookingResponse findByIdAndOwnerOrBookerId(Long bookingId, Long ownerOrBookerId);

    List<BookingResponse> findByBookerIdAndState(Long bookerId, String state, Long from, int size);

    List<BookingResponse> findByItemOwnerIdAndState(Long ownerId, String state, Long from, int size);
}
