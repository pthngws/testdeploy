package com.group4.service;

import com.group4.model.UserModel;

public interface IPersonalInfoService {
    public UserModel fetchPersonalInfo(Long userID);
    public boolean savePersonalInfo(UserModel userModel, Long userID);
}
