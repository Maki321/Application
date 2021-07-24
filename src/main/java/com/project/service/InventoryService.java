package com.project.service;


import com.project.domain.CarModel;
import com.project.domain.CarPart;
import com.project.domain.repository.CarModelRepository;
import com.project.domain.repository.CarPartRepository;
import com.project.service.dto.CarModelDto;
import com.project.service.dto.CarModelPartsDto;
import com.project.service.dto.CarPartDto;
import com.project.service.request.CreateCarPartRequest;
import com.project.service.result.ActionResult;
import com.project.service.result.CarModelPartsResult;
import com.project.service.result.DataResult;
import com.project.service.result.SearchCarPartsResult;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InventoryService {

    private final CarPartRepository carPartRepository;
    private final CarModelRepository carModelRepository;

    public ActionResult createCarPart(CreateCarPartRequest request) {
        Optional<CarPart> existing = carPartRepository.findBySerialNumber(request.getSerialNumber());

        if (existing.isPresent())
            return new ActionResult(false, "Car part with serial number "
                    + existing.get().getSerialNumber() + " already exists");

        List<CarModel> carModels = carModelRepository.findAllByIdIn(request.getModelIds());

        if (carModels.isEmpty())
            return new ActionResult(false, "There are no car models for which new part is applicable");

        CarPart newCarPart = new CarPart();
        newCarPart.setName(request.getName());
        newCarPart.setSerialNumber(request.getSerialNumber());
        newCarPart.setCarModels(carModels);
        newCarPart.setManufactureDate(request.getManufactureDate());

        carPartRepository.save(newCarPart);

        return new ActionResult(true, "Successfully created car part");
    }

    public ActionResult deleteCarPart(Integer carPartId) {
        Optional<CarPart> carPartOpt = carPartRepository.findById(carPartId);

        if (carPartOpt.isEmpty())
            return new ActionResult(false, "There is no car part with id " + carPartId, HttpStatus.NOT_FOUND);

        CarPart carPart = carPartOpt.get();

        carPartRepository.delete(carPart);

        return new ActionResult(true, "Successfully deleted car part");
    }

    public DataResult<SearchCarPartsResult> searchCarPartsBySerialNumber(String serialNumber) {
        List<CarPart> carParts = carPartRepository.findAllBySerialNumber(serialNumber);
        SearchCarPartsResult response = new SearchCarPartsResult();

        response.setParts(convertPartsToDto(carParts));

        return new DataResult<>(true, response);
    }

    public DataResult<SearchCarPartsResult> searchCarPartsByManufactureDate(LocalDate from, LocalDate to) {
        List<CarPart> carParts;
        if (from!=null && to!=null)
            carParts=carPartRepository.findAllByManufacturedDate(from,to);
        else if (to==null)
            carParts=carPartRepository.findAllByManufacturedDate1(from);
        else
            carParts=carPartRepository.findAllByManufacturedDate2(to);

        SearchCarPartsResult response = new SearchCarPartsResult();

        response.setParts(convertPartsToDto(carParts));

        return new DataResult<>(true, response);
    }

    public DataResult<SearchCarPartsResult> searchCarPartsByFullModelName(String fullName) {
        List<CarPart> carParts = carPartRepository.findAllByFullModelName(fullName);
        SearchCarPartsResult response = new SearchCarPartsResult();

        response.setParts(convertPartsToDto(carParts));

        return new DataResult<>(true, response);
    }

    public DataResult<CarModelPartsResult> getModelParts() {
        List<CarModel> carModels = carModelRepository.findAll();

        CarModelPartsResult response = new CarModelPartsResult();
        response.setModels(convertModelsToModelPartsDto(carModels));

        return new DataResult<>(true, response);
    }

    private List<CarModelPartsDto> convertModelsToModelPartsDto(List<CarModel> carModels) {
        return carModels.stream().map(m -> {
            CarModelPartsDto carModelPartsDto = new CarModelPartsDto();
            carModelPartsDto.setFullName(m.getCarBrand().getName() + " " + m.getName());
            carModelPartsDto.setCount(m.getParts().size());
            carModelPartsDto.setParts(convertPartsToDto(m.getParts(), true));

            return carModelPartsDto;
        }).collect(Collectors.toList());
    }

    private List<CarPartDto> convertPartsToDto(List<CarPart> carParts) {
        return convertPartsToDto(carParts, false);
    }

    private List<CarPartDto> convertPartsToDto(List<CarPart> carParts, boolean skipModels) {
        return carParts.stream().map(p -> {
            CarPartDto carPartDto = new CarPartDto();
            carPartDto.setId(p.getId());
            carPartDto.setSerialNumber(p.getSerialNumber());
            carPartDto.setName(p.getName());
            carPartDto.setManufactureDate(p.getManufactureDate());

            if (!skipModels)
                carPartDto.setModels(p.getCarModels().stream().map(m -> {
                    CarModelDto carModelDto = new CarModelDto();
                    carModelDto.setModelId(m.getId());
                    carModelDto.setFullName(m.getCarBrand().getName() + " " + m.getName());

                    return carModelDto;
                }).collect(Collectors.toList()));

            return carPartDto;
        }).collect(Collectors.toList());
    }

}