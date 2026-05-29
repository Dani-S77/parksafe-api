package com.parksafe.api.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import com.parksafe.api.dto.request.TicketRequest;
import com.parksafe.api.dto.response.TicketResponse;
import com.parksafe.api.service.SpaceService;
import com.parksafe.api.service.TicketService;
import com.parksafe.api.service.VehicleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/tickets")
@RestController
@RequiredArgsConstructor
public class TicketController {

  private final TicketService ticketService;

  @PostMapping()
  public ResponseEntity<TicketResponse> openTicket(@Valid @RequestBody TicketRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ticketService.openTicket(request));
  }

  @PatchMapping("/{uuid}/checkout")
  public ResponseEntity<TicketResponse> checkout(@PathVariable UUID uuid) {
    return ResponseEntity.ok(ticketService.checkout(uuid));
  }

  @GetMapping("/{uuid}")
  public ResponseEntity<TicketResponse> findByUuid(@PathVariable UUID uuid) {
    return ResponseEntity.ok(ticketService.findByUuid(uuid));
  }
}
