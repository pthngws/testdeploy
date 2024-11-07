package com.group4.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailModel {
    private Long productDetailID;
    private String RAM;
    private String CPU;
    private String GPU;
    private String monitor;
    private String charger;
    private String disk;
    private String connect;
    private String LAN;
    private String WIFI;
    private String bluetooth;
    private String audio;
    private String webcam;
    private String operationSystem;
    private String weight;
    private String color;
    private String size;
    private String description;
    private List<ImageItemModel> images;
}
