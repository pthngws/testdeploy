package com.group4.service;

import java.util.List;
import java.util.Optional;


import com.group4.entity.ProductDetailEntity;

public interface IProductDetailService {

	List<ProductDetailEntity> findAll();
	
	Optional<ProductDetailEntity> findById(Long id);
	
	ProductDetailEntity save(ProductDetailEntity productDetailEntity);

	void deleteById(Long id);

}
