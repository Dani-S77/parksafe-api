package com.parksafe.api.dto.request;

import com.parksafe.api.entity.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VehicleRequest {
  @NotBlank(message = "the brand is obligatory")
  @Pattern(regexp = "^[A-Z]{3}[0-9]{3}$", message = "the Brand must have colombian format: 3 letters uppercase and 3 digits(Example: ABC123)")
  @Size(max = 6, message = "the Brand must not be exceed the 6 characters")
  private String plate;

  @NotNull(message = "the vehicle type is obligatory")
  private VehicleType vehicleType;

  @NotBlank(message = "the plate is obligatory")
  @Size(max = 50, message = "the plate must not be exceed the 40 characters")
  private String brand;

  @NotBlank(message = " the owner name is obligatory")
  @Size(max = 255, message = "the owner name wouldn't exceed the 255 characters")
  private String ownerName;

  @NotBlank(message = "The phone owner is obligatory")
  @Pattern(regexp = "^[0-9]{10}$", message = "the phone must have exactly 10 digits")
  private String ownerPhone;
}