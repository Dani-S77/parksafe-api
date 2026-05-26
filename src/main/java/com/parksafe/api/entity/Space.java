package com.parksafe.api.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "spaces")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Space {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, updatable = false)
  private UUID uuid;

  @Column(name = "space_number", nullable = false)
  private Integer spaceNumber;

  @Column(nullable = false, length = 100)
  private String sede;

  @Enumerated(EnumType.STRING)
  @Column(name = "space_type", nullable = false, length = 30)
  private SpaceType spaceType;

  @Enumerated(EnumType.STRING)
  @Column(name = "vehicle_type", nullable = false, length = 20)
  private VehicleType vehicleType;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  @Builder.Default
  private SpaceStatus status = SpaceStatus.AVAILABLE;

  @Column(name = "hourly_rate", nullable = false, precision = 10, scale = 2)
  private BigDecimal hourlyRate;

  @PrePersist
  public void generateUuid() {
    if (this.uuid == null) {
      this.uuid = UUID.randomUUID();
    }
  }
}
