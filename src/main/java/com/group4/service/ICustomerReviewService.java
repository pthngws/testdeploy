package com.group4.service;

import com.group4.entity.RateEntity;

public interface ICustomerReviewService {
    void saveReview(RateEntity review);
    boolean hasReviewed(Long userId, Long productId);
}
