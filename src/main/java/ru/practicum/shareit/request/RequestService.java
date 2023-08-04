package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.CreateRequestRequest;
import ru.practicum.shareit.request.dto.CreateRequestResponse;
import ru.practicum.shareit.request.dto.GetRequestResponse;
import ru.practicum.shareit.request.model.Request;

import java.util.List;

public interface RequestService {
    CreateRequestResponse save(CreateRequestRequest createRequestRequest, Long requestorId);

    Request findById(Long requestId);

    List<GetRequestResponse> findByRequestorId(Long requestorId);

    List<GetRequestResponse> findRequestsForAnotherRequestors(Long requestorId, Long from, int size);

    GetRequestResponse findById(Long requestId, Long userId);
}
