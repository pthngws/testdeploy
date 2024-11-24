package com.group4.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group4.entity.ProductEntity;
import com.group4.repository.ProductRepository;
import com.group4.service.IProductService;


@Service
public class ProductService implements IProductService{

	@Autowired
	private ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public List<ProductEntity> findAll() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}

	@Override
	public Optional<ProductEntity> findById(Long id) {
		// TODO Auto-generated method stub
		return productRepository.findById(id);
	}

	@Override
	public ProductEntity save(ProductEntity productEntity) {
		// TODO Auto-generated method stub
		return productRepository.save(productEntity);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		productRepository.deleteById(id);
	}

}
