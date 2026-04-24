package com.parksafe.api.dto.response;

import com.parksafe.api.entity.VehicleType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VehicleResponse {

  private String plate;
  private VehicleType vehicleType;
  private String brand;
  private String ownerName;
  private String ownerPhone;
  private Boolean active;
}
