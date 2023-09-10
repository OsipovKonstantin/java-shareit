package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

import static ru.practicum.shareit.util.Constants.DATE_TIME_PATTERN;

public interface CommentResponse {
    Long getId();

    String getText();

    @Value("#{target.getAuthor.getName}")
    String getAuthorName();

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    LocalDateTime getCreated();
}