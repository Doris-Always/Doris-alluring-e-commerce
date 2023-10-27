package com.example.ecommerce.dto.request;

import lombok.Data;

    @Data
    public class FindAllRequest {
        private int offset;
        private int pageSite;
        private String direction;
        private String field ;

    }
