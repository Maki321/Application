package com.project.controller;

import com.project.service.SalesService;
import com.project.service.request.CreateProductRequest;
import com.project.service.request.CreateSaleRequest;
import com.project.service.result.ActionResult;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sale")
@AllArgsConstructor
public class SalesController {

    private final SalesService salesService;

    @PostMapping("products")
    ResponseEntity<ActionResult> createProduct(@RequestBody CreateProductRequest request){
        return salesService.createProduct(request).intoResponseEntity();
    }

    @PostMapping("sales")
    ResponseEntity<ActionResult> createSale(@RequestBody CreateSaleRequest request){
        return salesService.createSale(request).intoResponseEntity();
    }
}
