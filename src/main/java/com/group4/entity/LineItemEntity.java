package com.group4.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lineItems")
public class LineItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @ToString.Exclude
    @JsonManagedReference
    private ProductEntity product;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @ToString.Exclude // Loại trừ khỏi toString()
    @JsonManagedReference
    private OrderEntity order;//

    public double getTotal() {
        return product.getPrice() * quantity;
    }
}
