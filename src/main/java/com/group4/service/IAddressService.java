package com.group4.service;

import com.group4.model.AddressModel;

public interface IAddressService {
    public AddressModel loadAddressFromJson();
    public boolean updateAddressForUser(AddressModel addressModel, Long addressID);

}
