package com.group4.repository;


import com.group4.entity.AddressEntity;
import com.group4.entity.CustomerEntity;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SpringBootApplication
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {


}
