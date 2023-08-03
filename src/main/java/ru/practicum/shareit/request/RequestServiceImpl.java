package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.RequestNotFoundException;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.util.OffsetBasedPageRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserService userService;

    @Override
    public Request save(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public Request findById(Long requestId) {
        return requestRepository.findById(requestId).orElseThrow(() -> new RequestNotFoundException(
                String.format("Запрос с id %d не найден.", requestId)));
    }

    @Override
    public List<Request> findByRequestorId(Long requestorId) {
        userService.findById(requestorId);
        return requestRepository.findByRequestorId(requestorId);
    }

    @Override
    public List<Request> findRequestsForAnotherRequestors(Long requestorId, OffsetBasedPageRequest page) {
        return requestRepository.findByRequestorIdNot(requestorId, page);
    }

    @Override
    public Request findById(Long requestId, Long userId) {
        userService.findById(userId);
        return findById(requestId);
    }
}
