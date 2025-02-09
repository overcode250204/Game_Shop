package com.A4Team.GamesShop.entities;

import com.A4Team.GamesShop.enums.UserRoleEnum;
import com.A4Team.GamesShop.enums.UserStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    @Column(name="status")
    private int status;

    public UserStatusEnum getStatusEnum() {
        return UserStatusEnum.fromValue(this.status);
    }

    public void setStatusEnum(UserStatusEnum status) {
        this.status = status.getValue();
    }


    public User(String email, String name, String avatar) {
        this.email = email;
        this.name = name;
        this.avatar = avatar;
    }

}
