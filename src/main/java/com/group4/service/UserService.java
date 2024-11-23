package com.group4.service;

import com.group4.entity.UserEntity;
import com.group4.repository.UserRepository;


import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Optional;



@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    
    public Long findIdByEmail(String email) {
        return userRepository.findIdByEmail(email);
    }



    
    public UserEntity findById(Long id) {
        return userRepository.findById(id).get();
    }

    
    public void recoverPassword(String password, String email) {
        UserEntity user = userRepository.findByEmail(email).get();
        user.setPassword(password);
        userRepository.save(user);
    }

    
    public boolean validateCredentials(String username, String password) {
        return userRepository.existsByEmailAndPasswordAndActive(username, password, true);
    }

    

    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

   
}
