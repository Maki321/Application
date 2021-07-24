package com.project.service.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateCarPartRequest {
    private String name;
    private String serialNumber;
    private List<Integer> modelIds;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate manufactureDate;
}
