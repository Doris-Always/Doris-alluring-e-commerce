package com.example.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@RequiredArgsConstructor
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String token;
    @NotNull
    private LocalDateTime createdAt;

    private LocalDateTime confirmAt;
    @NotNull
    private LocalDateTime expiredAt;
    @ManyToOne
    @JoinColumn(name = "user_token", referencedColumnName = "id")
    private User user;

    public ConfirmationToken(String token,LocalDateTime createdAt,LocalDateTime expiredAt,User user){
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.user = user;
    }


}

