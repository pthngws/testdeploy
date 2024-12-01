package com.group4.service;

import java.util.List;

public interface IInventoryService {
    List<Object[]> findProductNameAndQuantityByProductId(Long productId);
}
