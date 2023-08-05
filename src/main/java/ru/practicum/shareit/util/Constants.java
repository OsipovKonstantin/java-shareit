package ru.practicum.shareit.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;

@UtilityClass
public class Constants {
    public static final String USER_ID_IN_REQUEST_HEADER = "X-Sharer-User-Id";
    public static final Sort SORT_BY_START_DESC = Sort.by("start").descending();
    public static final Sort SORT_BY_CREATED_DESC = Sort.by("created").descending();
}