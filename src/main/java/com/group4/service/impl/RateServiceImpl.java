package com.group4.service.impl;

import com.group4.entity.RateEntity;
import com.group4.repository.RateRepository;
import com.group4.service.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateServiceImpl implements IRateService {
    @Autowired
    private RateRepository rateRepository;


    public void respondToRate(Long rateID, String response) {
        // Tìm đánh giá
        RateEntity rate = rateRepository.findById(rateID)
                .orElseThrow(() -> new RuntimeException("Rate not found"));

        // Kiểm tra xem đã có phản hồi chưa
        if (rate.getResponse() != null) {
            throw new RuntimeException("Đánh giá đã được phản hồi");
        }

        // Lưu phản hồi
        rate.setResponse(response);
        rateRepository.save(rate);
    }

    @Override
    public List<RateEntity> getAllRates() {
        return rateRepository.findAll(); // Lấy tất cả các đánh giá
    }

    @Override
    public RateEntity getRateById(Long rateID) {
        return rateRepository.findById(rateID).orElseThrow(() -> new RuntimeException("Đánh giá không tồn tại"));
    }

    @Override
    public void saveRate(RateEntity rate) {
        rateRepository.save(rate); // Lưu đánh giá sau khi cập nhật phản hồi
    }

}
