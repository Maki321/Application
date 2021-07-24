package com.project.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class CarPart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private LocalDate manufactureDate;
    private String serialNumber;

    @ManyToMany
    @JoinTable(
            name = "CarPartModel",
            joinColumns = {@JoinColumn(name = "PartId")},
            inverseJoinColumns = {@JoinColumn(name = "ModelId")}
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CarModel> carModels;
}
