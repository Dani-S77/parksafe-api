package com.parksafe.api.service;

import com.parksafe.api.dto.request.VehicleRequest;
import com.parksafe.api.dto.response.VehicleResponse;

public interface VehicleService {
  VehicleResponse create(VehicleRequest request);

  VehicleResponse findByPlate(String plate);
}
