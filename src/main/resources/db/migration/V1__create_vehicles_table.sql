CREATE TABLE vehicles (
  id      BIGSERIAL PRIMARY KEY,
  plate   VARCHAR(6) NOT NULL UNIQUE,
  brand   VARCHAR(20) NOT NULL,
  vehicle_type VARCHAR(50) NOT NULL,
  owner_name VARCHAR(255) NOT NULL,
  owner_phone VARCHAR(10) NOT NULL,
  active BOOLEAN NOT NULL DEFAULT TRUE,

   CONSTRAINT chk_vehicle_type CHECK(vehicle_type IN('CAR','BUS','TRUCK','MOTORCICLE'))
  
);

CREATE INDEX idx_vehicles_plate on vehicles(plate);
