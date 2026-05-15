package com.parksafe.api.dto.request;

import java.math.BigDecimal;

import com.parksafe.api.entity.SpaceType;
import com.parksafe.api.entity.VehicleType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SpaceRequest {

  @NotNull(message = "Space Number is required")
  @Positive(message = "the space number must be a positive number")
  private Integer spaceNumber;

  @NotBlank(message = "sede is required")
  private String sede;

  @NotNull(message = " the space type is required")
  private SpaceType spaceType;

  @NotNull(message = "the vehicle type is required")
  private VehicleType vehicleType;

  @NotNull(message = "the hourly Rate is required")
  @DecimalMin(value = "0.01", message = "Hourly rate must be greater than zero")
  @Digits(integer = 8, fraction = 2, message = "Hourly rate format is invalid")
  private BigDecimal hourlyRate;
}
