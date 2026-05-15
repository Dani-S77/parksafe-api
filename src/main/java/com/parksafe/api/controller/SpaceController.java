package com.parksafe.api.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parksafe.api.dto.request.SpaceRequest;
import com.parksafe.api.dto.response.SpaceResponse;
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

  public ResponseEntity<List<>>

}
