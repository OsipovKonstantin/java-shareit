package ru.practicum.shareit.item;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.dto.CommentResponse;
import ru.practicum.shareit.item.model.Comment;

@UtilityClass
public class CommentMapper {
    public static CommentResponse toCommentResponse(Comment comment) {
        return new CommentResponse(comment.getId(),
                comment.getText(),
                comment.getAuthor().getName(),
                comment.getCreated());
    }
}