package com.group4.service;

import com.group4.model.LineItemModel;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IPurchasedProductService {
    List<LineItemModel> getAllLineItems(Long orderId);
}
