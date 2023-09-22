package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.booking.dto.CreateBookingRequest;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static ru.practicum.shareit.util.Constants.USER_ID_HEADER;

@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> save(@RequestHeader(USER_ID_HEADER) long bookerId,
                                       @RequestBody @Valid CreateBookingRequest createBookingRequest) {
        log.info("Creating booking {}, userId={}", createBookingRequest, bookerId);
        return bookingClient.save(bookerId, createBookingRequest);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> update(@PathVariable Long bookingId,
                                         @RequestHeader(USER_ID_HEADER) long ownerId,
                                         @RequestParam boolean approved) {
        log.info("Update booking {}, ownerId={}, approved={}", bookingId, ownerId, approved);
        return bookingClient.update(bookingId, ownerId, approved);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> findByIdAndUserId(@RequestHeader(USER_ID_HEADER) long userId,
                                                    @PathVariable Long bookingId) {
        log.info("Get booking {}, userId={}", bookingId, userId);
        return bookingClient.findByIdAndUserId(userId, bookingId);
    }

    @GetMapping
    public ResponseEntity<Object> findByBookerIdAndState(@RequestHeader(USER_ID_HEADER) long bookerId,
                                                         @RequestParam(name = "state", defaultValue = "all") String state,
                                                         @Min(0) @Max(Long.MAX_VALUE) @RequestParam(name = "from",
                                                                 defaultValue = "0") Integer from,
                                                         @Min(1) @Max(100) @RequestParam(name = "size",
                                                                 defaultValue = "10") Integer size) {
        BookingState validatedState = BookingState.from(state)
                .orElseThrow(() -> new IllegalArgumentException("Unknown state: " + state));
        log.info("Get booking with state {}, bookerId={}, from={}, size={}", state, bookerId, from, size);
        return bookingClient.findByBookerIdAndState(bookerId, validatedState, from, size);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> findByItemOwnerIdAndState(@RequestHeader(USER_ID_HEADER) Long ownerId,
                                                            @RequestParam(name = "state", defaultValue = "all") String state,
                                                            @Min(0) @Max(Long.MAX_VALUE) @RequestParam(name = "from",
                                                                    defaultValue = "0") Integer from,
                                                            @Min(1) @Max(100) @RequestParam(name = "size",
                                                                    defaultValue = "10") Integer size) {
        BookingState validatedState = BookingState.from(state)
                .orElseThrow(() -> new IllegalArgumentException("Unknown state: " + state));
        log.info("Get booking with state {}, ownerId={}, from={}, size={}", state, ownerId, from, size);
        return bookingClient.findByItemOwnerIdAndState(ownerId, validatedState, from, size);
    }
}
