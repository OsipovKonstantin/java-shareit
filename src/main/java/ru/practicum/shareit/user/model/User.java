package ru.practicum.shareit.user.model;

import lombok.*;
import lombok.experimental.Accessors;
import ru.practicum.shareit.validation.CreateGroup;
import ru.practicum.shareit.validation.UpdateGroup;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String name;

    @Column(length = 320, unique = true)
    @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
            groups = {CreateGroup.class, UpdateGroup.class})
    private String email;
}