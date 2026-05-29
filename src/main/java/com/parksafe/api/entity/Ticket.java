package com.parksafe.api.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "tickets")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, unique = true, updatable = false)
  private UUID uuid;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "vehicle_plate", referencedColumnName = "plate")
  private Vehicle vehicle;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "space_id")
  private Space space;

  @Column(name = "entry_time", nullable = false)
  private Instant entryTime;

  @Column(name = "exit_time")
  private Instant exitTime;

  @Column(length = 511)
  private String observations;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  @Builder.Default
  private TicketStatus status = TicketStatus.ACTIVE;

  @Column(name = "hours_charged")
  private Integer hoursCharged;

  @Column(name = "subtotal_before_tax", precision = 12, scale = 2)
  private BigDecimal subtotalBeforeTax;

  @Column(name = "tax_amount", precision = 12, scale = 2)
  private BigDecimal taxAmount;

  @Column(name = "total_Amount", precision = 12, scale = 2)
  private BigDecimal totalAmount;
}
