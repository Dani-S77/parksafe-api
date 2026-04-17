package com.parksafe.api.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "/vehicles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, unique = true, length = 6)
  private String plate;

  @Column(nullable = false, length = 50)
  private String brand;

  @Enumerated(EnumType.STRING)
  @Column(name = "vehicle_type", nullable = false, length = 20)
  private VehicleType vehicleType;

  @Column(name = "owner_name", nullable = false, length = 255)
  private String ownerName;

  @Column(name = "owner_phone", nullable = false, length = 10)
  private String ownerPhone;

  @Column(nullable = false)
  @Builder.Default
  private Boolean active = True;

  @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY)
  private List<Ticket> tickets;
}