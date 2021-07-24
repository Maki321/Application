package com.project.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class CarModelPartsDto {
    private String fullName;
    private int count;
    private List<CarPartDto> parts;
}
