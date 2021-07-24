package com.project.domain.repository;

import com.project.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findBySerialNumber(String serialNumber);
}
