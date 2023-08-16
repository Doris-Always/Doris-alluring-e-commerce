package com.example.ecommerce.dto.request;

import lombok.Data;

@Data
public class OtpRequest {
    private String receiver;
    private String message;
    private String subject;

}
