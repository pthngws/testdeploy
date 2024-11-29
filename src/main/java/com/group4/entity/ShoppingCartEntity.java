package com.group4.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shoppingCarts")
public class ShoppingCartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartID;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonManagedReference
    @ToString.Exclude
    private CustomerEntity customer;

    @ManyToMany
    @JoinTable(
            name = "cart_products", // Tên bảng trung gian
            joinColumns = @JoinColumn(name = "cart_id"), // Khóa ngoại trỏ đến ShoppingCartEntity
            inverseJoinColumns = @JoinColumn(name = "product_id"), // Khóa ngoại trỏ đến ProductEntity
            uniqueConstraints = @UniqueConstraint(columnNames = {"cart_id", "product_id"}) // Ràng buộc duy nhất
    )
    @JsonManagedReference
    @ToString.Exclude
    private List<ProductEntity> products;


}
