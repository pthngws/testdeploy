package com.group4.service.impl;

import com.group4.entity.RateEntity;
import com.group4.repository.CustomerReviewRepository;
import com.group4.repository.RateRepository;
import com.group4.service.ICustomerReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerReviewServiceImpl implements ICustomerReviewService {

    @Autowired
    private RateRepository rateRepository;

    @Override
    public void saveReview(RateEntity rate) {
        rateRepository.save(rate);
    }

    @Override
    public boolean hasReviewed(Long userId, Long productId) {
        return rateRepository.existsByUser_UserIDAndProduct_ProductID(userId, productId);
    }
}
