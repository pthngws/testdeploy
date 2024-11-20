package com.group4.repository;

import com.group4.entity.UserEntity;
import com.group4.model.AddressModel;
import com.group4.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalInfoRepository extends JpaRepository<UserEntity, Long> {

    // Truy vấn thông tin người dùng từ database
    default UserEntity retrieveInfoFormDB() {
        return findById(2L).orElse(null); // Giả sử userId = 1, có thể lấy từ session hoặc token
    }

    // Lưu thông tin cá nhân vào database
    default void saveInfoToDB(UserEntity userEntity) {
        save(userEntity);
    }
}

