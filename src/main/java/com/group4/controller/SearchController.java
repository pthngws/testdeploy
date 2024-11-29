package com.group4.controller;

import com.group4.entity.*;
import com.group4.model.*;
import com.group4.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SearchController {
    @Autowired
    ProductServiceImpl productServiceImpl = new ProductServiceImpl();
    @GetMapping("/search-products")
    public String listProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String searchName,
            @RequestParam(required = false) String manufacturer,
            @RequestParam(required = false) String cpu,
            @RequestParam(required = false) String gpu,
            @RequestParam(required = false) String operationSystem,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String disk,
            @RequestParam(required = false) String category,
            Model model) {

        Page<ProductEntity> productEntities = productServiceImpl.searchProducts(
                searchName, manufacturer, cpu, gpu, operationSystem, minPrice, maxPrice, disk, category, PageRequest.of(page, 12));

//        List<ProductModel> products = productEntities.stream()
//                .map(this::convertToModel)
//                .collect(Collectors.toList());

        model.addAttribute("products", productEntities);
        model.addAttribute("page", productEntities);

        // Gửi danh sách category để hiển thị trên giao diện
        List<CategoryModel> categories = productServiceImpl.getAllCategories();
        model.addAttribute("categories", categories);

        return "shop-grid-left-sidebar";
    }



    public ProductModel convertToModel(ProductEntity entity) {
        ProductModel model = new ProductModel();
        model.setProductID(entity.getProductID());
        model.setName(entity.getName());
        model.setPrice(entity.getPrice());
        model.setStatus(entity.getStatus());

        if (entity.getCategory() != null) {
            model.setCategory(convertToCategoryModel(entity.getCategory()));
        }

        if (entity.getManufacturer() != null) {
            model.setManufacturer(convertToManufacturerModel(entity.getManufacturer()));
        }

        if (entity.getDetail() != null)
            model.setDetail(convertToProductDetailModel(entity.getDetail()));

        return model;
    }

    public CategoryModel convertToCategoryModel(CategoryEntity entity) {
        CategoryModel model = new CategoryModel();
        model.setCategoryID(entity.getCategoryID());
        model.setName(entity.getName());
        model.setDescription(entity.getDescription());
        return model;
    }

    public ManufacturerModel convertToManufacturerModel(ManufacturerEntity entity) {
        ManufacturerModel model = new ManufacturerModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setAddress(entity.getAddress());
        // Thêm các thuộc tính khác nếu có
        return model;
    }

    public ProductDetailModel convertToProductDetailModel(ProductDetailEntity entity) {

        ProductDetailModel model = new ProductDetailModel();
        model.setProductDetailID(entity.getProductDetailID());
        model.setDescription(entity.getDescription());
        model.setAudio(entity.getAudio());
        model.setColor(entity.getColor());
        model.setCharger(entity.getCharger());
        model.setBluetooth(entity.getBluetooth());
        model.setConnect(entity.getConnect());
        model.setCPU(entity.getCPU());
        model.setDisk(entity.getDisk());
        model.setGPU(entity.getGPU());

        List<ImageItemModel> imageItemModels = convertToImageItemModels(entity.getImages());
        model.setImages(imageItemModels);
        model.setLAN(entity.getLAN());
        model.setMonitor(entity.getMonitor());
        model.setOperationSystem(entity.getOperationSystem());
        model.setRAM(entity.getRAM());
        model.setSize(entity.getSize());
        model.setWebcam(entity.getWebcam());
        model.setWeight(entity.getWeight());
        model.setWIFI(entity.getWIFI());

        return model;
    }

    public ImageItemModel convertToImageItemModel(ImageItemEntity entity) {
        ImageItemModel model = new ImageItemModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setImageUrl(entity.getImageUrl());
        return model;
    }

    public List<ImageItemModel> convertToImageItemModels(List<ImageItemEntity> entities) {
        return entities.stream()
                .map(this::convertToImageItemModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/payment")
    public String showPaymentPage(@RequestParam("orderId") String orderId,
                                  @RequestParam("amount") String amount,
                                  Model model) {
        // Truyền orderId và amount vào model
        model.addAttribute("orderId", orderId);
        model.addAttribute("amount", amount);
        return "payment"; // Trả về template payment.html
    }
}
