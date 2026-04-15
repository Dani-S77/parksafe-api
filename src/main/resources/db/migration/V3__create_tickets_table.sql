CREATE TABLE tickets (
 id                  BIGSERIAL     PRIMARY KEY,     
 uuid                UUID          NOT NULL UNIQUE DEFAULT gen_random_uuid(),
 vehicle_plate       VARCHAR(6)     NOT NULL,
 space_id            BIGINT        NOT NULL,
 entry_time          TIMESTAMPTZ   NOT NULL,
 exit_time           TIMESTAMPTZ,
 observations        VARCHAR(511),
 status              VARCHAR(10)     NOT NULL DEFAULT 'ACTIVE',
 hours_charged       INTEGER,
 subtotal_before_tax NUMERIC(12, 2), 
 tax_amount          NUMERIC(12, 2),
 total_amount        NUMERIC(12, 2),


 CONSTRAINT fk_ticket_vehicle FOREIGN KEY(vehicle_plate) REFERENCES vehicles(plate),
 CONSTRAINT fk_ticket_space FOREIGN KEY(space_id) REFERENCES spaces(id),
 CONSTRAINT chk_ticket_status CHECK (status IN ('ACTIVE','CLOSED')) 

);

CREATE INDEX idx_tickets_uuid ON tickets(uuid);
CREATE INDEX idx_tickets_vehicle_plate ON tickets(vehicle_plate);
CREATE INDEX idx_tickets_status ON tickets(status);
CREATE INDEX idx_tickets_entry_time ON tickets(entry_time);
CREATE INDEX idx_tickets_exit_time ON tickets(exit_time);


