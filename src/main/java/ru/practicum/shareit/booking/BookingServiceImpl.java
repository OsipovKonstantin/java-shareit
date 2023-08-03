package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ItemService itemService;

    @Override
    public Booking save(Booking booking) {
        if (Objects.equals(itemService.findById(booking.getItem().getId()).getOwner().getId(),
                booking.getBooker().getId()))
            throw new WrongUserIdException("Создание брони не доступно для владельца предмета.");

        if (!booking.getItem().getAvailable())
            throw new ItemNotAvailableException("Предмет не доступен для бронирования.");
        if (booking.getStart().isAfter(booking.getEnd()) || booking.getStart().isEqual(booking.getEnd()))
            throw new StartNotBeforeEndException("Время начала использования вещи должно быть строго раньше " +
                    "времени окончания.");
        return bookingRepository.save(booking);
    }

    @Override
    public Booking update(Long bookingId, Long ownerId, boolean approved) {
        Booking oldBooking = findById(bookingId);
        if (!Objects.equals(oldBooking.getItem().getOwner().getId(), ownerId))
            throw new WrongUserIdException("Обновление статуса брони доступно только для владельцев предметов.");
        if (!Objects.equals(oldBooking.getStatus(), Status.WAITING))
            throw new StatusAlreadyChangedException(String.format("Статус был изменён владельцем предмета ранее на %s",
                    oldBooking.getStatus()));
        return bookingRepository.save(oldBooking.setStatus(approved ? Status.APPROVED : Status.REJECTED));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Booking findByIdAndOwnerOrBookerId(Long bookingId, Long ownerOrBookerId) {
        Booking booking = findById(bookingId);
        if (!Objects.equals(booking.getItem().getOwner().getId(), ownerOrBookerId)
                && !Objects.equals(booking.getBooker().getId(), ownerOrBookerId))
            throw new WrongUserIdException(String.format("Пользователь с id %d не является владельцем " +
                    "предмета или бронирующим, поэтому информация о брони недоступна.", ownerOrBookerId));
        return booking;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Page<Booking> findByBookerIdAndState(Long bookerId, String state, Pageable page) {
        userService.findById(bookerId);

        switch (state) {
            case "ALL":
                return bookingRepository.findByBookerIdOrderByStartDesc(bookerId, page);
            case "CURRENT":
                return bookingRepository.findByBookerIdAndStartIsBeforeAndEndIsAfterOrderByStart(bookerId,
                        LocalDateTime.now(), LocalDateTime.now(), page);
            case "PAST":
                return bookingRepository.findByBookerIdAndEndIsBeforeOrderByStartDesc(bookerId, LocalDateTime.now(), page);
            case "FUTURE":
                return bookingRepository.findByBookerIdAndStartIsAfterOrderByStartDesc(bookerId, LocalDateTime.now(), page);
            case "WAITING":
                return bookingRepository.findByBookerIdAndStatusOrderByStartDesc(bookerId, Status.WAITING, page);
            case "REJECTED":
                return bookingRepository.findByBookerIdAndStatusOrderByStartDesc(bookerId, Status.REJECTED, page);
            default:
                throw new UnknownStateException(String.format("Передано неподдерживаемое состояние бронирования %s",
                        state));
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Page<Booking> findByItemOwnerIdAndState(Long ownerId, String state, Pageable page) {
        userService.findById(ownerId);

        switch (state) {
            case "ALL":
                return bookingRepository.findByItemOwnerId(ownerId, page);
            case "CURRENT":
                return bookingRepository.findByItemOwnerIdAndStartIsBeforeAndEndIsAfter(ownerId,
                        LocalDateTime.now(), LocalDateTime.now(), page);
            case "PAST":
                return bookingRepository.findByItemOwnerIdAndEndIsBefore(ownerId, LocalDateTime.now(),
                        page);
            case "FUTURE":
                return bookingRepository.findByItemOwnerIdAndStartIsAfter(ownerId, LocalDateTime.now(),
                        page);
            case "WAITING":
                return bookingRepository.findByItemOwnerIdAndStatus(ownerId, Status.WAITING, page);
            case "REJECTED":
                return bookingRepository.findByItemOwnerIdAndStatus(ownerId, Status.REJECTED, page);
            default:
                throw new UnknownStateException(String.format("Передано неподдерживаемое состояние бронирования %s",
                        state));
        }
    }

    private Booking findById(Long id) {
        return bookingRepository.findById(id).orElseThrow(
                () -> new BookingNotFoundException(String.format("Бронь с id %d не найдена.", id)));
    }
}
