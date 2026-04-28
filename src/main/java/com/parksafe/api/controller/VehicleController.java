package com.parksafe.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parksafe.api.dto.request.VehicleRequest;
import com.parksafe.api.dto.response.VehicleResponse;
import com.parksafe.api.service.VehicleService;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

  private final VehicleService vehicleService;

  @PostMapping
  public ResponseEntity<VehicleResponse> create(@Valid @RequestBody VehicleRequest request) {
    VehicleResponse response = vehicleService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/{plate}")
  public ResponseEntity<VehicleResponse> findByPlate(@PathVariable String plate){
      VehicleResponse response=vehicleService.findByPlate(plate);
      return ResponseEntity.ok(response);
  }

}
