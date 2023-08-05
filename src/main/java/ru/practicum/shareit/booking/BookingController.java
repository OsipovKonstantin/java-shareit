package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingResponse;
import ru.practicum.shareit.booking.dto.CreateBookingRequest;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

import static ru.practicum.shareit.util.Constants.USER_ID_IN_REQUEST_HEADER;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingResponse save(@RequestHeader(USER_ID_IN_REQUEST_HEADER) Long bookerId,
                                @RequestBody @Valid CreateBookingRequest createBookingRequest) {
        return bookingService.save(bookerId, createBookingRequest);
    }

    @PatchMapping("/{bookingId}")
    public BookingResponse update(@PathVariable Long bookingId,
                                  @RequestHeader(USER_ID_IN_REQUEST_HEADER) Long ownerId,
                                  @RequestParam boolean approved) {
        return bookingService.update(bookingId, ownerId, approved);
    }

    @GetMapping("{bookingId}")
    public BookingResponse findByIdAndOwnerOrBookerId(@PathVariable Long bookingId,
                                                      @RequestHeader(USER_ID_IN_REQUEST_HEADER) Long ownerOrBookerId) {
        return bookingService.findByIdAndOwnerOrBookerId(bookingId, ownerOrBookerId);
    }

    @GetMapping
    public List<BookingResponse> findByBookerIdAndState(@RequestHeader(USER_ID_IN_REQUEST_HEADER) Long bookerId,
                                                        @RequestParam(defaultValue = "ALL") String state,
                                                        @RequestParam(defaultValue = "0") @Min(0) @Max(Long.MAX_VALUE) Long from,
                                                        @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        return bookingService.findByBookerIdAndState(bookerId, state, from, size);
    }

    @GetMapping("/owner")
    public List<BookingResponse> findByItemOwnerIdAndState(@RequestHeader(USER_ID_IN_REQUEST_HEADER) Long ownerId,
                                                           @RequestParam(defaultValue = "ALL") String state,
                                                           @RequestParam(defaultValue = "0") @Min(0) @Max(Long.MAX_VALUE) Long from,
                                                           @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        return bookingService.findByItemOwnerIdAndState(ownerId, state, from, size);
    }
}