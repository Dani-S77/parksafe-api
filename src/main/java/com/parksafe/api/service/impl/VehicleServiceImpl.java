package com.parksafe.api.service.impl;

import org.aspectj.util.LangUtil.ProcessController.Thrown;
import org.springframework.stereotype.Service;

import com.parksafe.api.dto.request.VehicleRequest;
import com.parksafe.api.dto.response.VehicleResponse;
import com.parksafe.api.entity.Vehicle;
import com.parksafe.api.exception.ConflictException;
import com.parksafe.api.repository.VehicleRepository;
import com.parksafe.api.service.VehicleService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

  private final VehicleRepository vehicleRepository;

  @Override
  @Transactional
  public VehicleResponse create(VehicleRequest request) {
    if (vehicleRepository.existsByPlate(request.getPlate())) {
      throw new ConflictException("Vehicle Already exists",
          "Exists a vehicle with plate: " + request.getPlate());
    }
    Vehicle vehicle = Vehicle.builder()
        .plate(request.getPlate())
        .brand(request.getBrand())
        .vehicleType(request.getVehicleType())
        .ownerPhone(request.getOwnerPhone())
        .ownerName(request.getOwnerName())
        .build();

    Vehicle saved = vehicleRepository.save(vehicle);
    return toResponse(saved);

  }

  public VehicleResponse toResponse(Vehicle vehicle) {
    return VehicleResponse.builder()
        .plate(vehicle.getPlate())
        .vehicleType(vehicle.getVehicleType())
        .brand(vehicle.getBrand())
        .ownerName(vehicle.getOwnerName())
        .ownerPhone(vehicle.getOwnerPhone())
        .active(vehicle.getActive())
        .build();

  }

  @Override
  public VehicleResponse findByPlate(String plate) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByPlate'");
  }
}
