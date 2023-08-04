package ru.practicum.shareit.request;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.request.dto.CreateRequestResponse;
import ru.practicum.shareit.request.dto.GetRequestResponse;
import ru.practicum.shareit.request.model.Request;

import java.util.stream.Collectors;

@UtilityClass
public class RequestMapper {
    public CreateRequestResponse toCreateRequestResponse(Request request) {
        return new CreateRequestResponse(
                request.getId(),
                request.getDescription(),
                request.getCreated());
    }

    public GetRequestResponse toGetRequestResponse(Request request) {
        return new GetRequestResponse(
                request.getId(),
                request.getDescription(),
                request.getCreated(),
                request.getItems().stream().map(ItemMapper::toItemResponse).collect(Collectors.toList()));
    }
}
