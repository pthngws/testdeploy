package com.group4.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.group4.entity.CategoryEntity;



@Service
public interface ICategoryService {
	
	List<CategoryEntity> findAll();
	
	Optional<CategoryEntity> findById(Long id);
	
	CategoryEntity save(CategoryEntity category);
	
	void deleteById(Long id);
	
}
