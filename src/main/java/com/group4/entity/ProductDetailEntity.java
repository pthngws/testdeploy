package com.group4.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="productDetails" )
public class ProductDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Long productDetailID;

    @Column(columnDefinition = "nvarchar(250)")
    private String RAM;

    @Column(columnDefinition = "nvarchar(250)")
    private String CPU;

    @Column(columnDefinition = "nvarchar(250)")
    private String GPU;

    @Column(columnDefinition = "nvarchar(250)")
    private String monitor;

    @Column(columnDefinition = "nvarchar(250)")
    private String charger;

    @Column(columnDefinition = "nvarchar(250)")
    private String disk;

    @Column(columnDefinition = "nvarchar(250)")
    private String connect;

    @Column(columnDefinition = "nvarchar(250)")
    private String LAN;

    @Column(columnDefinition = "nvarchar(250)")
    private String WIFI;

    @Column(columnDefinition = "nvarchar(250)")
    private String bluetooth;

    @Column(columnDefinition = "nvarchar(250)")
    private String audio;

    @Column(columnDefinition = "nvarchar(250)")
    private String webcam;

    @Column(name = "operation_system", columnDefinition = "nvarchar(250)")
    private String operationSystem;

    @Column(columnDefinition = "nvarchar(250)")
    private String weight;

    @Column(columnDefinition = "nvarchar(250)")
    private String color;

    @Column(columnDefinition = "nvarchar(250)")
    private String size;

    @Column(columnDefinition = "nvarchar(250)")
    private String description;

    @OneToMany(mappedBy = "productDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageItemEntity> images;
}
