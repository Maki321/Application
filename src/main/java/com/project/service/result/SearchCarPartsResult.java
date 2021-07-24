package com.project.service.result;

import com.project.service.dto.CarPartDto;
import lombok.Data;

import java.util.List;

@Data
public class SearchCarPartsResult {
    private List<CarPartDto> parts;
}
