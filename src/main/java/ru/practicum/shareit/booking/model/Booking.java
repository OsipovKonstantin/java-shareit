package ru.practicum.shareit.booking.model;

import lombok.*;
import lombok.experimental.Accessors;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "bookings")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"})
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "start_date")
    private LocalDateTime start;

    @Column(name = "end_date")
    private LocalDateTime end;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booker_id")
    private User booker;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
}