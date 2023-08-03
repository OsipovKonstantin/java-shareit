package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.item.dto.ItemResponse;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class GetRequestResponse {
    private Long id;
    private String description;
    private LocalDateTime created;
    private List<ItemResponse> items;
}
