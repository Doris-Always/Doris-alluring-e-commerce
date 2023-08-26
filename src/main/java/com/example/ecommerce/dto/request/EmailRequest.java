package com.example.ecommerce.dto.request;

import lombok.Data;

@Data
public class EmailRequest {
    private String receiver;
    private String message;

}
