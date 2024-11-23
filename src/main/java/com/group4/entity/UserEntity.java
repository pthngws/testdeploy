package com.group4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userID;

    @Column(columnDefinition = "nvarchar(250) not null")
    private String name;

    @Email
    @Column(columnDefinition = "nvarchar(200) not null", unique = true)
    private String email;

    @Column(columnDefinition = "nvarchar(100) not null")
    private String password;

    @Column(columnDefinition = "nvarchar(20)")
    private String gender;

    @Column(columnDefinition = "nvarchar(10)")
    private String phone;

    @Column(columnDefinition = "nvarchar(10)")
    private String roleNName;

    @Column(name = "active", nullable = false)
    private boolean active = true; // Mặc định tài khoản được kích hoạt

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private AddressEntity address;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoleNName() {
        return roleNName;
    }

    public void setRoleNName(String roleNName) {
        this.roleNName = roleNName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }
}

