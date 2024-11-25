package com.group4.service;

import java.util.List;
import java.util.Optional;


import com.group4.entity.ProductEntity;

public interface IProductService {

	List<ProductEntity> findAll();

	Optional<ProductEntity> findById(Long id);

	ProductEntity save(ProductEntity productEntity);

	void deleteById(Long id);
}
