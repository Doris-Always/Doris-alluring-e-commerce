package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.CreateAddressReq;
import com.example.ecommerce.model.Address;
import com.example.ecommerce.repository.AddressRepository;
import com.example.ecommerce.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressRepository addressRepository;
    @Override
    public Address createAddress(CreateAddressReq createAddressReq) {
        Address address = Address.builder().
                streetName(createAddressReq.getStreetName()).
                streetNumber(createAddressReq.getStreetNumber()).
                city(createAddressReq.getCity()).
                town(createAddressReq.getTown()).build();
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Long id, Address address) {
       Address foundAddress = getAddressById(id);
       foundAddress.setStreetName(address.getStreetName());
       foundAddress.setStreetNumber(address.getStreetNumber());
       foundAddress.setTown(address.getTown());
       foundAddress.setCity(address.getCity());
       return addressRepository.save(foundAddress);

    }

    @Override
    public Address getAddressById(Long id) {
        Address foundAddress = addressRepository.findById(id).orElseThrow(()->new IllegalArgumentException("address not found"));
        return foundAddress;
    }
}
