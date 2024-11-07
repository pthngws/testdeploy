package com.group4.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userID;

    @Column(columnDefinition = "nvarchar(250) not null")
    private String name;

    @Column(columnDefinition = "nvarchar(200) not null", unique = true)
    private String email;

    @Column(columnDefinition = "nvarchar(100) not null")
    private String password;

    @Column(columnDefinition = "nvarchar(20)")
    private String gender;

    @Column(columnDefinition = "nvarchar(10)")
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private AddressEntity address;

}

