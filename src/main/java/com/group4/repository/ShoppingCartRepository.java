package com.group4.repository;

import com.group4.entity.ShoppingCartEntity;
import com.group4.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity,Long> {
    Optional<ShoppingCartEntity> findByUser(UserEntity user);
}
