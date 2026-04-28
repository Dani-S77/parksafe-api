package com.parksafe.api.service.impl;

import org.aspectj.util.LangUtil.ProcessController.Thrown;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parksafe.api.dto.request.VehicleRequest;
import com.parksafe.api.dto.request.VehicleUpdateRequest;
import com.parksafe.api.dto.response.VehicleResponse;
import com.parksafe.api.entity.Vehicle;
import com.parksafe.api.exception.ConflictException;
import com.parksafe.api.repository.VehicleRepository;
import com.parksafe.api.service.VehicleService;

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
  @Transactional(readOnly = true)
  public VehicleResponse findByPlate(String plate) {
    Vehicle vehicle = getVehicleOrThrow(plate);
    return toResponse(vehicle);

  }

  @Override
  @Transactional
  public void update(String plate, VehicleUpdateRequest request) {
    Vehicle vehicle = getVehicleOrThrow(plate);
    boolean hasChanges = false;

    if (request.getVehicleType() != null &&
        !request.getVehicleType().equals(vehicle.getVehicleType())) {
          vehicle.setVehicleType(request.getVehicleType());
          hasChanges =true;
    }
    if(request.getBrand() != null &&
         !request.getBrand().equals(vehicle.getBrand())){
          vehicle.setBrand(request.getBrand());
          hasChanges=true;
         }
    if(request.getOwnerName() != null &&
        !request.getOwnerName().equals(vehicle.getOwnerName())){
          vehicle.setOwnerName(vehicle.getOwnerName());
          hasChanges=true;
        }
      if(request.getOwnerPhone() != null &&
            !request.getOwnerPhone().equals(vehicle.getOwnerPhone())){
              vehicle.setOwnerPhone(request.getOwnerPhone());
              hasChanges=true;
            }
      if(request.getActive()!= null &&
          !request.getActive()equals(vehicle.getActive())){
            vehicle.setActive(request.getActive());
            hasChanges=true;
          }

        if(!hasChanges){
          throw new ConflictException(
            "NO_CHANGES_DETECTED",
            "The request doesn't have anything camp difrent to the  storage"
          );
          vehicleRepository.save(vehicle);
        }

  public void delete(String plate) {
    Vehicle vehicle = getVehicleOrThrow(plate);

    boolean hasActiveTickets = vehicle.getTickets() != null &&
        vehicle.getTickets().stream().anyMatch(t -> "ACTIVE".equals(t.getStatus().name()));

    if (hasActiveTickets) {
      throw new ConflictException(
          "VEHICLE_HAS_ACTIVE_TICKETS",
          "The vehicle have one active ticket, it can't be deleted");
    }
    boolean hasAnyTickets = vehicle.getTickets() != null &&
        !vehicle.getTickets().isEmpty();

    if (hasAnyTickets) {
      vehicle.setActive(false);
      vehicleRepository.save(vehicle);
    } else {
      vehicleRepository.delete(vehicle);
    }
  }

}
