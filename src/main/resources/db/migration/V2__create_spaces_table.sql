CREATE TABLE spaces (
   id BIGSERIAL PRIMARY KEY,
   uuid UUID  NOT NULL UNIQUE DEFAULT gen_random_uuid(),
   space_number INTEGER NOT NULL,
   sede    VARCHAR(100) NOT NULL,
   space_type  VARCHAR(30) NOT NULL,
   vehicle_type VARCHAR(20) NOT NULL,
   status  VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',
   hourly_rate NUMERIC(10, 2) NOT NULL, 

CONSTRAINT chk_space_type CHECK(space_type IN('AVAILABLE','UNCOVERED','HANDICAPPED','ELECTRIC_VEHICLE')),

CONSTRAINT chk_space_vehicle_type CHECK(vehicle_type IN('CAR','AUTOBUS','TRUCK','MOTORCYCLE')),

CONSTRAINT chk_status CHECK (status IN('AVAILABLE','OCCUPIED')),

CONSTRAINT chk_hourly_rate CHECK(hourly_rate>0),

CONSTRAINT uq_space_number_sede UNIQUE (space_number, sede)
);

CREATE INDEX idx_spaces_uuid On spaces(uuid);
CREATE INDEX idx_spaces_status ON spaces(status);
CREATE INDEX idx_spaces_vehicle_type ON spaces(vehicle_type);
