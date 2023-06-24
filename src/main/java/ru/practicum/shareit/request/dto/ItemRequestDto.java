package ru.practicum.shareit.request.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class ItemRequestDto {
    private long id;
    @NotBlank
    private String description;
    @NotNull
    private User requestor;
    @NotNull
    private LocalDateTime created;
}
