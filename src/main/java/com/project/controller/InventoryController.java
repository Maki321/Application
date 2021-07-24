package com.project.controller;


import com.project.service.InventoryService;
import com.project.service.request.CreateCarPartRequest;
import com.project.service.result.ActionResult;
import com.project.service.result.CarModelPartsResult;
import com.project.service.result.DataResult;
import com.project.service.result.SearchCarPartsResult;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Objects;

@RestController
@RequestMapping("inventory")
@AllArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PutMapping("parts")
    public ResponseEntity<ActionResult> createCarPart(@RequestBody CreateCarPartRequest request) {
        return inventoryService.createCarPart(request).intoResponseEntity();
    }

    @DeleteMapping("parts/{id}")
    public ResponseEntity<ActionResult> deleteCarPart(@PathVariable Integer id) {
        return inventoryService.deleteCarPart(id).intoResponseEntity();
    }

    @GetMapping("parts")
    public ResponseEntity<DataResult<SearchCarPartsResult>> searchCarParts(
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate to,
            @RequestParam(required = false) String serialNumber,
            @RequestParam(required = false) String fullModelName
    ) {
        boolean hasDateSearch = Objects.nonNull(from) || Objects.nonNull(to);
        boolean hasSerialSearch = Objects.nonNull(serialNumber);
        boolean hasModelSearch = Objects.nonNull(fullModelName);
        boolean hasAnySearch = hasDateSearch || hasSerialSearch || hasModelSearch;

        if ((hasDateSearch ? (hasSerialSearch || hasModelSearch) : (hasSerialSearch && hasModelSearch)) || !hasAnySearch)
            return new DataResult<>(false, "Search can be done by date (from, to), by serial number or full " +
                    "model name of the car. It is not possible to combine params except date (from, to).", HttpStatus.BAD_REQUEST, null
            ).intoResponseEntity();

        if (hasDateSearch)
            return inventoryService.searchCarPartsByManufactureDate(from, to).intoResponseEntity();
        else if (hasSerialSearch)
            return inventoryService.searchCarPartsBySerialNumber(serialNumber).intoResponseEntity();

        return inventoryService.searchCarPartsByFullModelName(fullModelName).intoResponseEntity();
    }

    @GetMapping("model-parts")
    public ResponseEntity<DataResult<CarModelPartsResult>> getModelParts() {
        return inventoryService.getModelParts().intoResponseEntity();
    }

}

