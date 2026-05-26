package com.parksafe.api.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parksafe.api.dto.request.SpaceRequest;
import com.parksafe.api.dto.request.SpaceUpdateRequest;
import com.parksafe.api.dto.response.SpaceResponse;
import com.parksafe.api.entity.Space;
import com.parksafe.api.entity.SpaceStatus;
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
  @Transactional(readOnly = true)
  public SpaceResponse findByUuid(UUID uuid) {
    return toResponse(getSpaceOrThrow(uuid));
  }

  @Override
  @Transactional(readOnly = true)
  public List<SpaceResponse> findAvailable() {
    return spaceRepository.findByStatus(SpaceStatus.AVAILABLE)
        .stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<SpaceResponse> findAvailableByVehicleType(VehicleType vehicleType) {
    return spaceRepository.findByVehicleTypeAndStatus(vehicleType, SpaceStatus.AVAILABLE)
        .stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void update(UUID uuid, SpaceUpdateRequest request) {
    Space space = getSpaceOrThrow(uuid);

    boolean hasChanged = false;

    if (request.getSede() != null &&
        !request.getSede().equals(space.getSede())) {
      space.setSede(request.getSede());
      hasChanged = true;
    }

    if (request.getSpaceType() != null &&
        !request.getSpaceType().equals(space.getSpaceType())) {
      space.setSpaceType(request.getSpaceType());
      hasChanged = true;
    }

    if (request.getHourlyRate() != null &&
        request.getHourlyRate().compareTo(space.getHourlyRate()) != 0) {
      space.setHourlyRate(request.getHourlyRate());
      hasChanged = true;
    }

    if (request.getStatus() != null &&
        !request.getStatus().equals(space.getStatus())) {
      space.setStatus(request.getStatus());
      hasChanged = true;
    }

    if (!hasChanged) {
      throw new ConflictException(
          "NO_CHANGES_DETECTED",
          "There is not changes detected");
    }

    spaceRepository.save(space);
  }

  @Override
  @Transactional
  public void delete(UUID uuid) {
    Space space = getSpaceOrThrow(uuid);

    if (SpaceStatus.OCCUPIED.equals(space.getStatus())) {
      throw new ConflictException(
          "SPACE_OCCUPED",
          "You can not delete a space with a active ticket.");
    }
    spaceRepository.delete(space);
  }
}
