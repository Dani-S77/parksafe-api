package com.parksafe.api.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

import com.parksafe.api.entity.SpaceStatus;
import com.parksafe.api.entity.SpaceType;
import com.parksafe.api.entity.VehicleType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpaceResponse {

  private UUID uuid;
  private Integer spaceNumber;
  private String sede;
  private SpaceType spaceType;
  private VehicleType vehicleType;
  private SpaceStatus status;
  private BigDecimal hourlyRate;
}
