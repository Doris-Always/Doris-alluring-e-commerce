package com.example.ecommerce.dto.request;

import lombok.Data;

@Data
public class EmailDetailDTO {
    private String sender;
    private String receipient;
    private String subject;


}
