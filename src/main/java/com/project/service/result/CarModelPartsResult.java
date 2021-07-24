package com.project.service.result;

import com.project.service.dto.CarModelPartsDto;
import lombok.Data;

import java.util.List;

@Data
public class CarModelPartsResult {
    List<CarModelPartsDto> models;
}
