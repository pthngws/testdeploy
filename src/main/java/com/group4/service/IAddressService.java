package com.group4.service;

import com.group4.model.AddressModel;

public interface IAddressService {
    boolean updateAddressForUser(AddressModel addressModel, Long addressID);

}
