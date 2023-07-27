package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchItemResponse {
    long id;
    String name;
    String description;
    boolean available;
}
