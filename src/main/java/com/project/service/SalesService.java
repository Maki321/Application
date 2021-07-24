package com.project.service;

import com.project.domain.Product;
import com.project.domain.Sale;
import com.project.domain.repository.ProductRepository;
import com.project.domain.repository.SaleRepository;
import com.project.service.request.CreateProductRequest;
import com.project.service.request.CreateSaleRequest;
import com.project.service.result.ActionResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SalesService {

    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;

    public ActionResult createProduct(CreateProductRequest request){
        Optional<Product> existing = productRepository.findBySerialNumber(request.getSerialNumber());

        if (existing.isPresent()){
            return new ActionResult(false,"Product with serial number: " +
                    request.getSerialNumber() + " already exists.");
        }

        Optional<Sale> saleOpt = saleRepository.findBySerialNumber(request.getSaleSerialNumber());

        if (saleOpt.isEmpty() && request.getSaleSerialNumber()!=null){
            return new ActionResult(false,"Sale with serial number: " +
                    request.getSaleSerialNumber() + " does not exist.");
        }

        Sale sale = saleOpt.orElse(null);
        Product newProduct = new Product();
        newProduct.setBasePrice(request.getBasePrice());
        newProduct.setSerialNumber(request.getSerialNumber());
        newProduct.setSale(sale);

        productRepository.save(newProduct);
        return new ActionResult(true,"Product Successfully created.");
    }

    public ActionResult createSale(CreateSaleRequest request) {
        Optional<Sale> saleOptional = saleRepository.findBySerialNumber(request.getSerialNumber());

        if (saleOptional.isPresent()){
            return new ActionResult(false,"Sale with serial number: "
            + request.getSerialNumber() + " already exists.");
        }

        List<Product> products = productRepository.findAllById(request.getProductIds());

        if (products.isEmpty() && !request.getProductIds().isEmpty()){
            return new ActionResult(false,"Products with those ids do not exist");
        }

        Sale sale = new Sale();
        sale.setDiscount(request.getDiscount());
        sale.setSaleStart(request.getSaleStart());
        sale.setSaleEnd(request.getSaleEnd());
        sale.setSerialNumber(request.getSerialNumber());
        sale.setProducts(products);
        saleRepository.save(sale);

        for(Product p : products){
            if (p.getSale()==null){
                p.setSale(sale);
                productRepository.save(p);
            }
        }

        return new ActionResult(true,"Sale has been created.");
    }
}
