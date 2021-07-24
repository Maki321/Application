package com.project.domain.repository;

import com.project.domain.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarModelRepository extends JpaRepository<CarModel, Integer> {

    List<CarModel> findAllByIdIn(List<Integer> ids);


}