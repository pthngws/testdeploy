package com.group4.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Getter
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id", nullable = false)
    @ToString.Exclude
    @JsonManagedReference
    private ManufacturerEntity manufacturer;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "detail_id", referencedColumnName = "detail_id")
    private ProductDetailEntity detail;

    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<ShoppingCartEntity> carts;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<RateEntity> rates;

    // Phương thức tính trung bình đánh giá
    public double getAverageRating() {
        if (rates == null || rates.isEmpty()) {
            return 0.0;  // Nếu không có đánh giá, trả về 0
        }
        return rates.stream()
                .mapToInt(RateEntity::getRate)
                .average()
                .orElse(0.0);
    }

    public int getReviewCount() {
        return (rates == null) ? 0 : rates.size();
    }
}
