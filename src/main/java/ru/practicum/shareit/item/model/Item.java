package ru.practicum.shareit.item.model;

import lombok.*;
import lombok.experimental.Accessors;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "items")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"})
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 2000)
    private String description;

    @Column(name = "is_available")
    private Boolean available;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", insertable = false, updatable = false)
    private List<Booking> bookings;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", insertable = false, updatable = false)
    private List<Comment> comments;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private Request request;
}
