package com.group4.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressModel {
    private Long addressID;
    private String country;
    private String province;
    private String district;
    private String commune;
    private String other;
}