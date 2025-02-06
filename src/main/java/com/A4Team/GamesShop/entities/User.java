package com.A4Team.GamesShop.entities;

import com.A4Team.GamesShop.enums.UserRoleEnum;
import com.A4Team.GamesShop.enums.UserStatusEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name="users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name="status")
    private UserStatusEnum status;

    @Column(name="google_code")
    private String googleCode;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="name")
    private String name;

    @Column(name="avatar")
    private String avatar;

    @Column(name="created_at", updatable=false)
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "total_reward_point", columnDefinition = "int default 0")
    private int totalRewardPoint;

    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private UserRoleEnum role;

}
