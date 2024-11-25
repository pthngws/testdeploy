package com.group4.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group4.entity.ManufacturerEntity;
import com.group4.repository.ManufacturersRepository;
import com.group4.service.IManufacturersService;


@Service
public class ManufacturersServiceImpl implements IManufacturersService{

	@Autowired
	private ManufacturersRepository manufacturersRepository;

	@Override
	public List<ManufacturerEntity> findAll() {
		// TODO Auto-generated method stub
		return manufacturersRepository.findAll();
	}

	@Override
	public Optional<ManufacturerEntity> findById(Long id) {
		// TODO Auto-generated method stub
		return manufacturersRepository.findById(id);
	}

	@Override
	public ManufacturerEntity save(ManufacturerEntity manufacturerEntity) {
		// TODO Auto-generated method stub
		return manufacturersRepository.save(manufacturerEntity);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		manufacturersRepository.deleteById(id);
	}

}
