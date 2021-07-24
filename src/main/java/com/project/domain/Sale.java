package com.project.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String serialNumber;
    private LocalDate saleStart;
    private LocalDate saleEnd;
    private double discount;
    @OneToMany(mappedBy = "sale")
    private List<Product> products;
}
