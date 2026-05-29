package com.parksafe.api.service;

import java.util.UUID;

import com.parksafe.api.dto.request.TicketRequest;
import com.parksafe.api.dto.response.TicketResponse;

public interface TicketService {

  TicketResponse openTicket(TicketRequest request);

  TicketResponse findByUuid(UUID uuid);

  TicketResponse checkout(UUID uuid);
}
