package com.parksafe.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parksafe.api.dto.request.SpaceRequest;
import com.parksafe.api.dto.request.SpaceUpdateRequest;
import com.parksafe.api.dto.response.SpaceResponse;
import com.parksafe.api.entity.VehicleType;
import com.parksafe.api.service.SpaceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/spaces")
@RequiredArgsConstructor
public class SpaceController {

  private final SpaceService spaceService;

  @PostMapping
  public ResponseEntity<SpaceResponse> create(@Valid @RequestBody SpaceRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(spaceService.create(request));
  }

  @GetMapping("/{uuid}")
  public ResponseEntity<SpaceResponse> findByUuid(@PathVariable UUID uuid) {
    return ResponseEntity.ok(spaceService.findByUuid(uuid));
  }

  @GetMapping("/available")
  public ResponseEntity<List<SpaceResponse>> findAvailable() {
    return ResponseEntity.ok(spaceService.findAvailable());
  }

  @GetMapping("/available/{vehicleType}")
  public ResponseEntity<List<SpaceResponse>> findAvailableByVehicleType(@PathVariable VehicleType vehicleType) {
    return ResponseEntity.ok(spaceService.findAvailableByVehicleType(vehicleType));
  }

  @PutMapping("/{uuid}")
  public ResponseEntity<Void> update(@PathVariable UUID uuid, @Valid @RequestBody SpaceUpdateRequest request) {
    spaceService.update(uuid, request);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{uuid}")
  public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
    spaceService.delete(uuid);
    return ResponseEntity.noContent().build();

  }

}
