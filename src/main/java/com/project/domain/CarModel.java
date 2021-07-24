package com.project.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "CarBrandId")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CarBrand carBrand;

    @ManyToMany
    @JoinTable(
            name = "CarPartModel",
            joinColumns = {@JoinColumn(name = "ModelId")},
            inverseJoinColumns = {@JoinColumn(name = "PartId")}
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CarPart> parts;
}
