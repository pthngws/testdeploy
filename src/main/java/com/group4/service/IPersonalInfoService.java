package com.group4.service;

import com.group4.entity.UserEntity;
import com.group4.model.UserModel;
import org.springframework.data.domain.Page;

public interface IPersonalInfoService {
    public UserModel fetchPersonalInfo(Long userID);
    public boolean savePersonalInfo(UserModel userModel, Long userID);
    // Tìm kiếm người dùng theo ID
    public UserEntity findUserById(Long userID);
}
