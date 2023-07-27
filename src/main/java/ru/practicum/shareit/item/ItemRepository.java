package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.dto.SearchItemResponse;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByOwnerId(Long userId);

    @Query(value = "select new ru.practicum.shareit.item.dto.SearchItemResponse(i.id, i.name, i.description, " +
            "i.available) " +
            "from Item as i " +
            "where i.available = true " +
            "and (lower(i.name) like lower(concat('%', ?1, '%')) " +
            "or lower(i.description) like lower(concat('%', ?1, '%')))")
    List<SearchItemResponse> searchAvailableItemsByText(String text);
}