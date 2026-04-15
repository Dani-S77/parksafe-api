INSERT INTO users(username, email, password, role)
    VALUES (
        'First Insertion',
        'FirstTest@GMAIL.COM',
        '99876543=)(/&%$#)(/&%$#)(/&%$',
        'ADMIN'

) ON CONFLICT (username) DO NOTHING;



INSERT INTO spaces(space_number, sede, space_type, vehicle_type, hourly_rate)
   VALUES
     (1, 'Sede Norte Bogotá', 'COVERED','CAR',3500.0),
     (2, 'Sede Sur Bogotá', 'COVERED', 'BUS', 3500.0)
ON CONFLICT DO NOTHING;
