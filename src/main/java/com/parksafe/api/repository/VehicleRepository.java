package com.parksafe.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parksafe.api.entity.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
  Optional<Vehicle> findByPlate(String plate);

  boolean existsByPlate(String plate);
}
