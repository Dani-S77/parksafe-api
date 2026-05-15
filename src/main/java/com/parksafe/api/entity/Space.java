package com.parksafe.api.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

  @Column()
  private UUID uuid;

  @Column()
  private Integer spaceNumber;

  @Column()
  private String sede;

  @Column()
  private SpaceType spaceType;

  @Column()
  private VehicleType vehicleType;

  @Column()
  private SpaceStatus status = SpaceStatus.AVAILABLE;

  @Column()
  private BigDecimal hourlyRate;

}
