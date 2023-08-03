package ru.practicum.shareit.request;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.util.OffsetBasedPageRequest;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByRequestorId(Long requestorId);

    List<Request> findByRequestorIdNot(Long requestorId, OffsetBasedPageRequest page);
}
