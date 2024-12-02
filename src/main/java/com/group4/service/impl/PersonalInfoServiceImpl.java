package com.group4.service.impl;

import com.group4.entity.AddressEntity;
import com.group4.entity.UserEntity;
import com.group4.model.AddressModel;
import com.group4.model.UserModel;
import com.group4.repository.AddressRepository;
import com.group4.repository.PersonalInfoRepository;
import com.group4.service.IPersonalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonalInfoServiceImpl implements IPersonalInfoService {

    @Autowired
    private PersonalInfoRepository repository;
    @Autowired
    private AddressRepository addressRepository;

    // Lấy thông tin cá nhân từ cơ sở dữ liệu
    public UserModel fetchPersonalInfo(Long userID) {
        UserEntity userEntity = repository.retrieveInfoFormDB(userID);
        if (userEntity != null) {
            // Đã có mối quan hệ OneToOne nên không cần gọi AddressRepository
            AddressEntity addressEntity = userEntity.getAddress(); // Địa chỉ sẽ được lấy trực tiếp từ userEntity
            AddressModel addressModel = null;
            if (addressEntity != null) {
                addressModel = new AddressModel(
                        addressEntity.getAddressID(),
                        addressEntity.getCountry(),
                        addressEntity.getProvince(),
                        addressEntity.getDistrict(),
                        addressEntity.getCommune(),
                        addressEntity.getOther()
                );
            }
            return new UserModel(
                    userEntity.getUserID(),
                    userEntity.getName(),
                    userEntity.getEmail(),
                    userEntity.getPassword(),
                    userEntity.getGender(),
                    userEntity.getPhone(),
                    userEntity.getRoleName(),
                    userEntity.isActive(),
                    addressModel
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
            UserEntity userEntity = new UserEntity(
                    userID, // Sử dụng userID hiện tại
                    userModel.getName(),
                    userModel.getEmail(),
                    password,
                    userModel.getGender(),
                    userModel.getPhone(),
                    role,
                    isActive,
                    addressEntity // Liên kết địa chỉ mới hoặc giữ nguyên địa chỉ cũ
            );

            // Lưu hoặc cập nhật thông tin người dùng
            repository.saveInfoToDB(userEntity);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public UserEntity findUserById(Long userID) {
        return repository.retrieveInfoFormDB(userID); // Directly return the user
    }
}
