package com.group4.service;

import com.group4.entity.AddressEntity;
import com.group4.entity.UserEntity;
import com.group4.model.AddressModel;
import com.group4.model.UserModel;
import com.group4.repository.AddressRepository;
import com.group4.repository.PersonalInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalInfoService {

    @Autowired
    private PersonalInfoRepository repository;
    @Autowired
    private AddressRepository addressRepository;

    // Lấy thông tin cá nhân từ cơ sở dữ liệu
    public UserModel fetchPersonalInfo(Long userID) {
        UserEntity entity = repository.retrieveInfoFormDB(userID); // Gọi repository để lấy thông tin từ DB
        if (entity != null) {
            return new UserModel(
                    entity.getUserID(),
                    entity.getName(),
                    entity.getEmail(),
                    entity.getPassword(),
                    entity.getGender(),
                    entity.getPhone(),
                    entity.getRoleName(),
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
    public boolean savePersonalInfo(UserModel userModel, Long userID) {
        try {
            if (userID == null) {
                // Nếu userID không tồn tại, không thể lưu
                return false;
            }

            // Lấy thông tin người dùng hiện tại từ DB
            UserEntity existingUserEntity = repository.retrieveInfoFormDB(userID);
            if (existingUserEntity == null) {
                // Nếu không tìm thấy người dùng, không thể lưu
                return false;
            }

            // Lấy các thông tin cần thiết
            String password = userModel.getPassword();
            if (password == null || password.isEmpty()) {
                password = existingUserEntity.getPassword(); // Giữ nguyên mật khẩu hiện tại
            }
            boolean isActive = existingUserEntity.isActive();
            String role = existingUserEntity.getRoleName();
            AddressModel addressModel = userModel.getAddress();
            AddressEntity addressEntity = existingUserEntity.getAddress();
            if (addressModel != null) {
                // Nếu có địa chỉ mới, cập nhật địa chỉ
                addressEntity = new AddressEntity(
                        addressEntity != null ? addressEntity.getAddressID() : null,
                        addressModel.getCountry(),
                        addressModel.getProvince(),
                        addressModel.getDistrict(),
                        addressModel.getCommune(),
                        addressModel.getOther()
                );
                addressRepository.save(addressEntity); // Lưu địa chỉ
            }

            // Tạo UserEntity từ UserModel
            UserEntity entity = new UserEntity(
                    userModel.getUserID(),
                    userModel.getName(),
                    userModel.getEmail(),
                    userModel.getPassword(),
                    userModel.getGender(),
                    userModel.getPhone(),
                    userModel.getRoleName(),
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

            // Lưu hoặc cập nhật thông tin người dùng
            repository.saveInfoToDB(entity);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
