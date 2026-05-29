package com.parksafe.api.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parksafe.api.entity.Ticket;
import com.parksafe.api.entity.TicketStatus;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

  Optional<Ticket> findByUuid(UUID uuid);

  boolean existsBySpaceAndStatus(Long spaceId, TicketStatus status);

  boolean existsByVehiclePlateAndStatus(String plate, TicketStatus status);

}
