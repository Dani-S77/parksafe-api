package com.parksafe.api.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.parksafe.api.entity.TicketStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketResponse {

  private UUID uuid;
  private String vehiclePlate;
  private UUID spaceUuid;
  private Integer spaceNumber;
  private String sede;
  private Instant entryTime;
  private Instant exitTime;
  private String observations;
  private TicketStatus status;
  private Integer hoursCharged;
  private BigDecimal subtotalBeforeTax;
  private BigDecimal taxAmount;
  private BigDecimal totalAmount;
}
