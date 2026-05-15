package com.parksafe.api.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parksafe.api.dto.request.SpaceRequest;
import com.parksafe.api.dto.request.SpaceUpdateRequest;
import com.parksafe.api.dto.response.SpaceResponse;
import com.parksafe.api.entity.Space;
import com.parksafe.api.entity.VehicleType;
import com.parksafe.api.exception.ConflictException;
import com.parksafe.api.exception.ResourceNotFoundException;
import com.parksafe.api.repository.SpaceRepository;
import com.parksafe.api.service.SpaceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpaceServiceImpl implements SpaceService {

  private final SpaceRepository spaceRepository;

  @Override
  @Transactional
  public SpaceResponse create(SpaceRequest request) {
    if (spaceRepository.existsBySpaceNumberAndSede(request.getSpaceNumber(), request.getSede())) {
      throw new ConflictException(
          "SPACE_ALREADY_EXISTS",
          "Space Number" + request.getSpaceNumber() +
              "already exists in sede" + request.getSede()

      );
    }
    Space space = Space.builder()
        .spaceNumber(request.getSpaceNumber())
        .sede(request.getSede())
        .spaceType(request.getSpaceType())
        .vehicleType(request.getVehicleType())
        .hourlyRate(request.getHourlyRate())
        .build();

    Space saved = spaceRepository.save(space);
    return toResponse(saved);
  }

  private SpaceResponse toResponse(Space space) {
    return SpaceResponse.builder()
        .uuid(space.getUuid())
        .spaceNumber(space.getSpaceNumber())
        .vehicleType(space.getVehicleType())
        .sede(space.getSede())
        .status(space.getStatus())
        .hourlyRate(space.getHourlyRate())
        .spaceType(space.getSpaceType())
        .build();
  }

  private Space getSpaceOrThrow(UUID uuid) {
    return spaceRepository.findByUuid(uuid)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Space_NOT_fOUND",
            "SPace not found by UUID: " + uuid));
  }

  @Override
  public SpaceResponse findByUuid(UUID uuid) {
    return toResponse(getSpaceOrThrow(uuid));
  }

  @Override
  public List<SpaceResponse> findAvailable() {

  }

  @Override
  public List<SpaceResponse> findAvailableByVehicleType(VehicleType vehicleType) {

  }

  @Override
  public Void update(SpaceUpdateRequest request, UUID uuid) {

  }

  @Override
  public Void delete(UUID uuid) {

  }
}
