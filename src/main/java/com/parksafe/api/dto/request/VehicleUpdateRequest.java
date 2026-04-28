package com.parksafe.api.dto.request;

import com.parksafe.api.entity.VehicleType;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VehicleUpdateRequest {

  private VehicleType vehicleType;

  @Size(max = 50)
  private String brand;

  @Size(max = 255)
  private String ownerName;

  @Pattern(regexp = "^[0-9]{10}$")
  private String ownerPhone;

  public Boolean active;
}
