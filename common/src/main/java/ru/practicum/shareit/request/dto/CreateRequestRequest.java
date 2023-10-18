package ru.practicum.shareit.request.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class CreateRequestRequest {
    @NotBlank
    @Size(max = 500)
    private String description;
}
