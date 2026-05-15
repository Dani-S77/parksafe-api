package com.parksafe.api.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parksafe.api.entity.Space;
import com.parksafe.api.entity.SpaceStatus;
import com.parksafe.api.entity.VehicleType;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {
  Optional<Space> findByUuid(UUID uuid);

  boolean existsBySpaceNumberAndSede(Integer spaceNumber, String sede);

  List<Space> findByStatus(SpaceStatus status);

  List<Space> findByVehicleTypeAndStatus(VehicleType vehicleType, SpaceStatus status);

}
