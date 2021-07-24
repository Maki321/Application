package com.project.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String serialNumber;
    private int basePrice;
    @ManyToOne
    @JoinColumn(name = "SaleId")
    private Sale sale;
}
