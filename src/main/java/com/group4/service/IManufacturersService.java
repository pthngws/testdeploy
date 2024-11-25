package com.group4.service;

import java.util.List;
import java.util.Optional;


import com.group4.entity.ManufacturerEntity;

public interface IManufacturersService {

	List<ManufacturerEntity> findAll();

	Optional<ManufacturerEntity> findById(Long id);

	ManufacturerEntity save(ManufacturerEntity category);

	void deleteById(Long id);

}
