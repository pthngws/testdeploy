package com.group4.service;

import java.util.List;
import java.util.Optional;

import com.group4.entity.ImageItemEntity;

public interface IImageItemService {
	
	List<ImageItemEntity> getAllImageItem();
		
	Optional<ImageItemEntity> findById(Long id);
	
	ImageItemEntity addImageItem(ImageItemEntity imageItem);
	
	ImageItemEntity updateImageItem(Long id, ImageItemEntity imageItem);
	
	void deleteImageItem(Long id);
}
