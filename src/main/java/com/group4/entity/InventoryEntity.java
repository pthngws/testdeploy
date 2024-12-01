package com.group4.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inventory")
public class InventoryEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long intvenory_id;
    
    
    @Column(name = "product_id")
    private Long productId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
    private ProductEntity product;

//    @OneToOne(fetch = FetchType.LAZY)
//    @MapsId // Sử dụng @MapsId để ánh xạ trực tiếp khóa chính từ ProductEntity
//    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
//    private ProductEntity product;
    
    @Column(nullable = false)
    private int quantity;
}
