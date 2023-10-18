package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.CreateRequestRequest;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static ru.practicum.shareit.util.Constants.USER_ID_HEADER;

@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class RequestController {
    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid CreateRequestRequest createRequestRequest,
                                       @RequestHeader(USER_ID_HEADER) Long requestorId) {
        log.info("Post request {}, requestorId={}", createRequestRequest, requestorId);
        return requestClient.save(createRequestRequest, requestorId);
    }

    @GetMapping
    public ResponseEntity<Object> findByRequestorId(@RequestHeader(USER_ID_HEADER) Long requestorId) {
        log.info("Get request with requestorId={}", requestorId);
        return requestClient.findByRequestorId(requestorId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findRequestsForAnotherRequestors(@RequestHeader(USER_ID_HEADER) Long requestorId,
                                                                   @RequestParam(defaultValue = "0") @Min(0) @Max(Long.MAX_VALUE) long from,
                                                                   @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        log.info("Get request with requestorId={}, from={}, size={}", requestorId, from, size);
        return requestClient.findRequestsForAnotherRequestors(requestorId, from, size);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> findById(@RequestHeader(USER_ID_HEADER) Long userId,
                                           @PathVariable Long requestId) {
        log.info("Get request with userId={}, requestId={}", userId, requestId);
        return requestClient.findDtoById(requestId, userId);
    }
}
