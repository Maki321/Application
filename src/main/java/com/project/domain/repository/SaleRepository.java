package com.project.domain.repository;

import com.project.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Integer> {
    Optional<Sale> findBySerialNumber(String saleSerialNumber);
}
