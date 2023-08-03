package ru.practicum.shareit.request;

import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.util.OffsetBasedPageRequest;

import java.util.List;

public interface RequestService {
    Request save(Request request);

    Request findById(Long requestId);

    List<Request> findByRequestorId(Long requestorId);

    List<Request> findRequestsForAnotherRequestors(Long requestorId, OffsetBasedPageRequest page);

    Request findById(Long requestId, Long userId);
}
