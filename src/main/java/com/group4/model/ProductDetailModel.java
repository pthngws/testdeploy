package com.group4.model;

import com.group4.entity.ProductDetailEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public ProductDetailModel(ProductDetailEntity detail) {
        this.productDetailID = detail.getProductDetailID();
        this.CPU = detail.getCPU();
        this.GPU = detail.getGPU();
        this.RAM = detail.getRAM();
        this.monitor = detail.getMonitor();
        this.charger = detail.getCharger();
        this.disk = detail.getDisk();
        this.connect = detail.getConnect();
        this.LAN = detail.getLAN();
        this.WIFI = detail.getWIFI();
        this.bluetooth = detail.getBluetooth();
        this.audio = detail.getAudio();
        this.webcam = detail.getWebcam();
        this.operationSystem = detail.getOperationSystem();
        this.weight = detail.getWeight();
        this.color = detail.getColor();
        this.size = detail.getSize();
        this.description = detail.getDescription();

        // Lấy danh sách hình ảnh nếu có
        if (detail.getImages() != null) {
            this.images = detail.getImages().stream()
                    .map(ImageItemModel::new) // Chuyển từ ImageItemEntity sang ImageItemModel
                    .collect(Collectors.toList());
        } else {
            this.images = new ArrayList<>(); // Nếu không có hình ảnh, khởi tạo danh sách rỗng
        }
    }
}
