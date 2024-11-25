package com.group4.repository;

import com.group4.entity.CustomerEntity;
import com.group4.entity.OrderFailEntity;
import com.group4.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderFailRepository extends JpaRepository<OrderFailEntity, Long> {
}
