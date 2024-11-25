package com.group4.service;

import com.group4.entity.UserEntity;

import java.util.Optional;

public interface IUserService {
    public Long findIdByEmail(String email);
    public UserEntity findById(Long id);
    public void recoverPassword(String password, String email);
    public boolean validateCredentials(String username, String password);
    public UserEntity saveUser(UserEntity user);
    public Optional<UserEntity> findByEmail(String email);
    public void updateActiveStatus(Long userId, boolean status);
}
