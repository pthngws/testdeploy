package com.group4.service;

import com.group4.entity.RateEntity;

import java.util.List;

public interface IRateService {
    public List<RateEntity> getAllRates();
    public RateEntity getRateById(Long rateID);
    public void saveRate(RateEntity rate);
}
