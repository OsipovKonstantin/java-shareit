package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.CreateBookingDto;
import ru.practicum.shareit.booking.dto.GetBookingDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public GetBookingDto save(@RequestHeader("X-Sharer-User-Id") Long bookerId,
                              @RequestBody @Valid CreateBookingDto createBookingDto) {
        return bookingService.save(bookerId, createBookingDto);
    }

    @PatchMapping("/{bookingId}")
    public GetBookingDto update(@PathVariable Long bookingId,
                                @RequestHeader("X-Sharer-User-Id") Long ownerId,
                                @RequestParam boolean approved) {
        return bookingService.update(bookingId, ownerId, approved);
    }

    @GetMapping("{bookingId}")
    public GetBookingDto findByIdAndOwnerOrBookerId(@PathVariable Long bookingId,
                                                    @RequestHeader("X-Sharer-User-Id") Long ownerOrBookerId) {
        return bookingService.findByIdAndOwnerOrBookerId(bookingId, ownerOrBookerId);
    }

    @GetMapping
    public List<GetBookingDto> findByBookerIdAndState(@RequestHeader("X-Sharer-User-Id") Long bookerId,
                                                      @RequestParam(defaultValue = "ALL") String state,
                                                      @RequestParam(defaultValue = "0") @Min(0) Long from,
                                                      @RequestParam(defaultValue = "10") @Min(1) int size) {
        return bookingService.findByBookerIdAndState(bookerId, state, from, size);
    }

    @GetMapping("/owner")
    public List<GetBookingDto> findByItemOwnerIdAndState(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                                                         @RequestParam(defaultValue = "ALL") String state,
                                                         @RequestParam(defaultValue = "0") @Min(0) Long from,
                                                         @RequestParam(defaultValue = "10") @Min(1) int size) {
        return bookingService.findByItemOwnerIdAndState(ownerId, state, from, size);
    }
}