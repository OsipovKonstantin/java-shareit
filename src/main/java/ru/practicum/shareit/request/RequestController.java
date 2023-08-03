package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.CreateRequestRequest;
import ru.practicum.shareit.request.dto.CreateRequestResponse;
import ru.practicum.shareit.request.dto.GetRequestResponse;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.util.OffsetBasedPageRequest;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class RequestController {
    private final RequestService requestService;
    private final UserService userService;

    @PostMapping
    public CreateRequestResponse save(@RequestBody @Valid CreateRequestRequest createRequestRequest,
                                      @RequestHeader("X-Sharer-User-Id") Long requestorId) {
        return RequestMapper.toCreateRequestResponse(requestService.save(
                new Request().setDescription(createRequestRequest.getDescription()).setRequestor(userService.findById(requestorId)))
        );
    }

    @GetMapping
    public List<GetRequestResponse> findByRequestorId(@RequestHeader("X-Sharer-User-Id") Long requestorId) {
        return requestService.findByRequestorId(requestorId).stream()
                .map(RequestMapper::toGetRequestResponse).collect(Collectors.toList());
    }

    @GetMapping("/all")
    public List<GetRequestResponse> findRequestsForAnotherRequestors(@RequestHeader("X-Sharer-User-Id") Long requestorId,
                                                                     @RequestParam(defaultValue = "0") @Min(0) Long from,
                                                                     @RequestParam(defaultValue = "10") @Min(1) int size) {
        return requestService.findRequestsForAnotherRequestors(
                        requestorId, new OffsetBasedPageRequest(from, size, Sort.by("created").descending())).stream()
                .map(RequestMapper::toGetRequestResponse).collect(Collectors.toList());
    }

    @GetMapping("/{requestId}")
    public GetRequestResponse findById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                       @PathVariable Long requestId) {
        return RequestMapper.toGetRequestResponse(requestService.findById(requestId, userId));
    }
}
