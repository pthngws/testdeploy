package com.group4.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group4.entity.ImageItemEntity;
import com.group4.repository.ImageRepository;
import com.group4.service.IImageItemService;

@Service
public class ImageItemServiceImpl implements IImageItemService{

	@Autowired
	private ImageRepository imageRepository;

	@Override
	public List<ImageItemEntity> getAllImageItem() {
		// TODO Auto-generated method stub
		return imageRepository.findAll();
	}

	@Override
	public Optional<ImageItemEntity> findById(Long id) {
		// TODO Auto-generated method stub
		return imageRepository.findById(id);
	}

	@Override
	public ImageItemEntity addImageItem(ImageItemEntity imageItem) {
		// TODO Auto-generated method stub
		return imageRepository.save(imageItem);
	}

	@Override
	public ImageItemEntity updateImageItem(Long id, ImageItemEntity imageItem) {
		// TODO Auto-generated method stub
		if (imageRepository.existsById(id)) {
            imageItem.setId(id);
            return imageRepository.save(imageItem);
		}
		return null;
	}

	@Override
	public void deleteImageItem(Long id) {
		// TODO Auto-generated method stub
		imageRepository.deleteById(id);
	}

}
