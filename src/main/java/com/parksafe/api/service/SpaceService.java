package com.parksafe.api.service;

import java.util.List;
import java.util.UUID;

import com.parksafe.api.dto.request.SpaceRequest;
import com.parksafe.api.dto.request.SpaceUpdateRequest;
import com.parksafe.api.dto.response.SpaceResponse;
import com.parksafe.api.entity.VehicleType;

public interface SpaceService {

  SpaceResponse create(SpaceRequest request);

  SpaceResponse findByUuid(UUID uuid);

  List<SpaceResponse> findAvailable();

  List<SpaceResponse> findAvailableByVehicleType(VehicleType vehicleType);

  Void update(UUID uuid, SpaceUpdateRequest request);

  Void delete(UUID uuid);

}
