package com.group4.service;

import com.group4.entity.AddressEntity;
import com.group4.entity.UserEntity;
import com.group4.model.AddressModel;
import com.group4.model.UserModel;
import com.group4.repository.PersonalInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonalInfoService {

    @Autowired
    private PersonalInfoRepository repository;

    // Lấy thông tin cá nhân từ cơ sở dữ liệu
    public UserModel fetchPersonalInfo() {
        UserEntity entity = repository.retrieveInfoFormDB(); // Gọi repository để lấy thông tin từ DB
        if (entity != null) {
            return new UserModel(
                    entity.getUserID(),
                    entity.getName(),
                    entity.getEmail(),
                    entity.getPassword(),
                    entity.getGender(),
                    entity.getPhone(),
                    entity.getRoleNName(),
                    entity.isActive(),
                    new AddressModel(
                            entity.getAddress().getAddressID(),
                            entity.getAddress().getCountry(),
                            entity.getAddress().getProvince(),
                            entity.getAddress().getDistrict(),
                            entity.getAddress().getCommune(),
                            entity.getAddress().getOther()
                    )
            );
        }
        return null;
    }

    // Lưu thông tin cá nhân vào cơ sở dữ liệu
    public boolean savePersonalInfo(UserModel userModel) {
        try {
            UserEntity entity = new UserEntity(
                    userModel.getUserID(),
                    userModel.getName(),
                    userModel.getEmail(),
                    userModel.getPassword(),
                    userModel.getGender(),
                    userModel.getPhone(),
                    userModel.getRoleNName(),
                    userModel.isActive(),
                    new AddressEntity(
                            userModel.getAddress().getAddressID(),
                            userModel.getAddress().getCountry(),
                            userModel.getAddress().getProvince(),
                            userModel.getAddress().getDistrict(),
                            userModel.getAddress().getCommune(),
                            userModel.getAddress().getOther()
                    )
            );
            repository.saveInfoToDB(entity); // Gọi repository để lưu vào DB
            return true;
        } catch (Exception e) {
            return false; // Xử lý lỗi nếu có
        }
    }
}
