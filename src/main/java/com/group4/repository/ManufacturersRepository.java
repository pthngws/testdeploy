package com.group4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group4.entity.ManufacturerEntity;

@Repository
public interface ManufacturersRepository extends JpaRepository<ManufacturerEntity, Long>{

}
