package com.parksafe.api.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TicketRequest {

  @NotBlank(message = "vehicle plate is required")
  @Pattern(regexp = "^[A-Z]{3}[0-9]{3}$", message = "Plate must follow colombian format: 3 uppercase letters and 3 digits")
  private String vehiclePlate;

  @NotNull(message = "space uuid is required")
  private UUID spaceUuid;

  @Size(max = 511, message = "Observations cannot exceed 511 characters")
  private String observations;
}
