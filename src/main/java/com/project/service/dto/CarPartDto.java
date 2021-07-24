package com.project.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CarPartDto {
    private int id;
    private String name;
    private LocalDate manufactureDate;
    private String serialNumber;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CarModelDto> models;

}
