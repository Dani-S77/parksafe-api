package com.parksafe.api.dto.request;

import java.math.BigDecimal;

import com.parksafe.api.entity.SpaceStatus;
import com.parksafe.api.entity.SpaceType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SpaceUpdateRequest {

  @Size(max = 100)
  private String sede;

  private SpaceType spaceType;

  @DecimalMin(value = "0.01", message = "Hourly must be greater than zero")
  @Digits(integer = 8, fraction = 2, message = "Hourly rate format is invalid")
  private BigDecimal hourlyRate;

  private SpaceStatus status;
}
