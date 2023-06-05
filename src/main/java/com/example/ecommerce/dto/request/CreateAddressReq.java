package com.example.ecommerce.dto.request;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CreateAddressReq {
    private String streetName;
    private String streetNumber;
    private String town;
    private String city;
}
