package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class UpdateItemRequest {
    @Size(max = 255)
    private String name;
    @Size(max = 2000)
    private String description;
    private Boolean available;
}
