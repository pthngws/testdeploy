package com.group4.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group4.entity.ProductDetailEntity;
import com.group4.repository.ProductDetailRepository;
import com.group4.service.IProductDetailService;


@Service
public class ProductDetailServiceImpl implements IProductDetailService{

	@Autowired
	private ProductDetailRepository productDetailRepository;



	@Override
	public List<ProductDetailEntity> findAll() {
		// TODO Auto-generated method stub
		return productDetailRepository.findAll();
	}


	@Override
	public Optional<ProductDetailEntity> findById(Long id) {
		// TODO Auto-generated method stub
		return productDetailRepository.findById(id);
	}


	@Override
	public ProductDetailEntity save(ProductDetailEntity productDetailEntity) {
		// TODO Auto-generated method stub
		return productDetailRepository.save(productDetailEntity);
	}


	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		productDetailRepository.deleteById(id);
	}


}
