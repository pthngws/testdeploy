package com.group4.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productID;

    @Column(columnDefinition = "nvarchar(250) not null")
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int status;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private ManufacturerEntity manufacturer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detail_id", referencedColumnName = "detail_id")
    private ProductDetailEntity detail;

    @ManyToMany(mappedBy = "products")
    private List<ShoppingCartEntity> carts;
}
