package com.example.ecommerce.service;

import com.example.ecommerce.dto.request.CreateAddressReq;
import com.example.ecommerce.dto.request.UpdateAddressReq;
import com.example.ecommerce.model.Address;

public interface AddressService {
    Address createAddress(CreateAddressReq createAddressReq);
    Address updateAddress(Long id, Address address);
    Address getAddressById(Long id);
}
