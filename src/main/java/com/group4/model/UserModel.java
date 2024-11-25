package com.group4.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private Long userID;
    private String name;
    private String email;
    private String password;
    private String gender;
    private String phone;
    private String roleName;
    private boolean active;
    private AddressModel address;
}
