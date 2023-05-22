package com.example.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    @Column(name = "email", unique=true)
    private String email;
    private String phoneNumber;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
