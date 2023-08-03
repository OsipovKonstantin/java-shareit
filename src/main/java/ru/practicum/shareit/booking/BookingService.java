package ru.practicum.shareit.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.booking.model.Booking;

public interface BookingService {
    Booking save(Booking booking);

    Booking update(Long bookingId, Long ownerId, boolean approved);

    Booking findByIdAndOwnerOrBookerId(Long bookingId, Long ownerOrBookerId);

    Page<Booking> findByBookerIdAndState(Long bookerId, String state, Pageable page);

    Page<Booking> findByItemOwnerIdAndState(Long ownerId, String state, Pageable page);
}
