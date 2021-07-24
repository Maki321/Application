package com.project.service.request;

import lombok.Data;

@Data
public class CreateProductRequest {
    private String name;
    private String serialNumber;
    private String saleSerialNumber;
    private int basePrice;
}
