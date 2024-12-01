package com.group4.service.impl;

import com.group4.entity.LineItemEntity;
import com.group4.model.LineItemModel;
import com.group4.model.ProductModel;
import com.group4.repository.PurchasedProductRepository;
import com.group4.service.IPurchasedProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchasedProductServiceImpl implements IPurchasedProductService {
    @Autowired
    private PurchasedProductRepository purchasedProductRepository;

    private LineItemModel convertToLineItemModel(LineItemEntity lineItemEntity) {
        LineItemModel lineItemModel = new LineItemModel();
        lineItemModel.setId(lineItemEntity.getId());
        lineItemModel.setProduct(new ProductModel(lineItemEntity.getProduct()));
        lineItemModel.setQuantity(lineItemEntity.getQuantity());
        lineItemModel.setTotal((int)lineItemEntity.getTotal());
        return lineItemModel;
    }

    @Override
    public List<LineItemModel> getAllLineItems(Long orderId){
        List<LineItemEntity> lineItemModels = purchasedProductRepository.findPurchasedProductsByOrderId(orderId);
        return lineItemModels.stream()
                .map(this::convertToLineItemModel)
                .collect(Collectors.toList());
    }

}
