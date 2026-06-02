package com.parksafe.api.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parksafe.api.dto.request.TicketRequest;
import com.parksafe.api.dto.response.TicketResponse;
import com.parksafe.api.entity.Space;
import com.parksafe.api.entity.SpaceStatus;
import com.parksafe.api.entity.Ticket;
import com.parksafe.api.entity.TicketStatus;
import com.parksafe.api.entity.Vehicle;
import com.parksafe.api.exception.ConflictException;
import com.parksafe.api.exception.ResourceNotFoundException;
import com.parksafe.api.repository.SpaceRepository;
import com.parksafe.api.repository.TicketRepository;
import com.parksafe.api.repository.VehicleRepository;
import com.parksafe.api.service.TicketService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

  private static final BigDecimal TAX_RATE = new BigDecimal("0.19");

  private final TicketRepository ticketRepository;
  private final VehicleRepository vehicleRepository;
  private final SpaceRepository spaceRepository;

  @Override
  @Transactional
  public TicketResponse openTicket(TicketRequest request) {

    Vehicle vehicle = vehicleRepository.findByPlate(request.getVehiclePlate())
        .orElseThrow(() -> new ResourceNotFoundException(
            "VEHICLE_NOT_FOUND",
            "no vehicle found by plate" + request.getVehiclePlate()));

    if (!vehicle.getActive()) {
      throw new ConflictException("VEHICLE_NOT_ACTIVE",
          "vehicle" + request.getVehiclePlate() + "is inactive");
    }

    if (ticketRepository.existsByVehiclePlateAndStatus(request.getVehiclePlate(), TicketStatus.ACTIVE)) {
      throw new ConflictException("VEHICLE_ALREADY_PARKED",
          "Vehicle" + request.getVehiclePlate() + "already has an active ticket");
    }

    Space space = spaceRepository.findByUuid(request.getSpaceUuid())
        .orElseThrow(() -> new ResourceNotFoundException("SPACE_NOT_FOUND",
            "No space found with uuid" + request.getSpaceUuid()));

    if (!vehicle.getVehicleType().equals(space.getVehicleType())) {
      throw new ConflictException("VEHICLE_TYPE_MISMATCH",
          "Space only accepts: " + space.getVehicleType() + "but vehicle is: " + vehicle.getVehicleType());
    }

    Ticket ticket = Ticket.builder()
        .vehicle(vehicle)
        .space(space)
        .entryTime(Instant.now())
        .observations(request.getObservations())
        .build();

    space.setStatus(SpaceStatus.OCCUPIED);
    spaceRepository.save(space);

    Ticket saved = ticketRepository.save(ticket);

    return toResponse(saved);

  }

  @Override
  @Transactional
  public TicketResponse checkout(UUID uuid) {

    Ticket ticket = ticketRepository.findByUuid(uuid)
        .orElseThrow(() -> new ResourceNotFoundException("TICKET_NOT_FOUND", "Ticket not found by uuid: " + uuid));

    if (TicketStatus.CLOSED.equals(ticket.getStatus())) {
      throw new ConflictException("TICKET_ALREADY_CLOSED", "Ticket: " + uuid + " is already closed");
    }

    Instant exitTime = Instant.now();
    long minutes = Duration.between(ticket.getEntryTime(), exitTime).toMinutes();

    int hoursCharged = (int) Math.max(1, Math.ceil(minutes / 60.0));

    BigDecimal hourlyRate = ticket.getSpace().getHourlyRate();
    BigDecimal subtotal = hourlyRate.multiply(BigDecimal.valueOf(hoursCharged));
    BigDecimal tax = subtotal.multiply(TAX_RATE).setScale(2, RoundingMode.HALF_UP);
    BigDecimal total = subtotal.add(tax);

    ticket.setExitTime(exitTime);
    ticket.setHoursCharged(hoursCharged);
    ticket.setSubtotalBeforeTax(subtotal);
    ticket.setTaxAmount(tax);
    ticket.setTotalAmount(total);
    ticket.setStatus(TicketStatus.CLOSED);

    Space space = ticket.getSpace();
    space.setStatus(SpaceStatus.AVAILABLE);

    spaceRepository.save(space);
    Ticket saved = ticketRepository.save(ticket);

    return toResponse(saved);
  }

  @Override
  @Transactional(readOnly = true)
  public TicketResponse findByUuid(UUID uuid) {
    Ticket ticket = ticketRepository.findByUuid(uuid)
        .orElseThrow(() -> new ResourceNotFoundException("TICKET_NOT_FOUND", "Ticket not found by uuid: " + uuid));
    return toResponse(ticket);
  }

  public TicketResponse toResponse(Ticket ticket) {
    return TicketResponse.builder()
        .uuid(ticket.getUuid())
        .vehiclePlate(ticket.getVehicle().getPlate())
        .spaceUuid(ticket.getSpace().getUuid())
        .spaceNumber(ticket.getSpace().getSpaceNumber())
        .sede(ticket.getSpace().getSede())
        .entryTime(ticket.getEntryTime())
        .exitTime(ticket.getExitTime())
        .observations(ticket.getObservations())
        .status(ticket.getStatus())
        .hoursCharged(ticket.getHoursCharged())
        .subtotalBeforeTax(ticket.getSubtotalBeforeTax())
        .taxAmount(ticket.getTaxAmount())
        .totalAmount(ticket.getTotalAmount())
        .build();
  }

}
