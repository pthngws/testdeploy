package com.group4.service;

import com.group4.entity.UserEntity;
import com.group4.model.UserModel;

public interface IPersonalInfoService {
    UserModel fetchPersonalInfo(Long userID);
    boolean savePersonalInfo(UserModel userModel, Long userID);
    // Tìm kiếm người dùng theo ID
    UserEntity findUserById(Long userID);
}
