package com.example.quiznew.store.entities.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User{

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
