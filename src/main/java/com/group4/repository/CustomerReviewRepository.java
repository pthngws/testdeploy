package com.group4.repository;

import com.group4.entity.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerReviewRepository extends JpaRepository<RateEntity, Long> {
}
