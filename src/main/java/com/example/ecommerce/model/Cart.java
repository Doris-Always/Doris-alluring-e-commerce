
package com.example.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Cart {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

//    private long customerId;

    @OneToMany(mappedBy = "cart")
//            (fetch = FetchType.EAGER)
    private List<CartProduct> cartProducts = new ArrayList<>();


}
