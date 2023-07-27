package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.dto.BookingDtoForGetItemResponse;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBookerIdOrderByStartDesc(Long bookerId);

    List<Booking> findByBookerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(Long bookerId,
                                                                              LocalDateTime now, LocalDateTime now1);

    List<Booking> findByBookerIdAndEndIsBeforeOrderByStartDesc(Long bookerId, LocalDateTime now);

    List<Booking> findByBookerIdAndStartIsAfterOrderByStartDesc(Long bookerId, LocalDateTime now);

    List<Booking> findByBookerIdAndStatusOrderByStartDesc(Long bookerId, Status status);

    List<Booking> findByItemOwnerIdOrderByStartDesc(Long ownerId);

    List<Booking> findByItemOwnerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(Long ownerId,
                                                                                 LocalDateTime now, LocalDateTime now1);

    List<Booking> findByItemOwnerIdAndEndIsBeforeOrderByStartDesc(Long ownerId, LocalDateTime now);

    List<Booking> findByItemOwnerIdAndStartIsAfterOrderByStartDesc(Long ownerId, LocalDateTime now);

    List<Booking> findByItemOwnerIdAndStatusOrderByStartDesc(Long ownerId, Status waiting);

    BookingDtoForGetItemResponse findFirstByItemIdAndStatusAndStartIsBeforeOrderByStartDesc(long itemId, Status status,
                                                                                            LocalDateTime now);

    BookingDtoForGetItemResponse findFirstByItemIdAndStatusAndStartIsAfterOrderByStartAsc(long itemId, Status status,
                                                                                          LocalDateTime now);

    Booking findFirstByBooker_IdAndItem_IdAndStatusAndEndIsBefore(Long authorId, Long itemId, Status approved,
                                                                  LocalDateTime now);
}
