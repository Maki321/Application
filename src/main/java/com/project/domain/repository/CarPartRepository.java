package com.project.domain.repository;

import com.project.domain.CarPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CarPartRepository extends JpaRepository<CarPart, Integer> {

    Optional<CarPart> findBySerialNumber(String serialNumber);

    @Query(value = "select * from CarPart where serialNumber LIKE :serialNumber% ",
            nativeQuery = true)
    List<CarPart> findAllBySerialNumber(@Param("serialNumber") String serialNumber);



    @Query(value = "Select * from CarPart where manufactureDate between :from and :to" ,
            nativeQuery = true)
    List<CarPart> findAllByManufacturedDate(@Param("from") LocalDate from, @Param("to")LocalDate to);

    @Query(value = "Select * from CarPart where manufactureDate >= :from",
            nativeQuery = true)
    List<CarPart> findAllByManufacturedDate1(@Param("from")LocalDate from);

    @Query(value = "Select * from CarPart where manufactureDate <= :to",
            nativeQuery = true)
    List<CarPart> findAllByManufacturedDate2(@Param("to")LocalDate to);



    @Query(value = "select CarPart.id,CarPart.Name,CarPart.ManufactureDate,CarPart.SerialNumber \n" +
            "FROM carModel\n" +
            "INNER JOIN carBrand\n" +
            "ON carModel.CarBrandId=carBrand.Id\n" +
            "INNER JOIN carPartModel\n" +
            "ON carModel.Id=carPartModel.Id\n" +
            "INNER JOIN carPart\n" +
            "ON carPartModel.id=carPart.Id\n" +
            "WHERE concat(carBrand.Name , ' ' , carModel.Name) = :fullName  ",
            nativeQuery = true)
    List<CarPart> findAllByFullModelName(@Param("fullName") String fullName);

}
