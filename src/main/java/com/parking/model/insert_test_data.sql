
INSERT INTO parking_spaces (zone, status) VALUES ('Zone A', 'AVAILABLE');
INSERT INTO parking_spaces (zone, status) VALUES ('Zone A', 'AVAILABLE');
INSERT INTO parking_spaces (zone, status) VALUES ('Zone B', 'AVAILABLE');
INSERT INTO parking_spaces (zone, status) VALUES ('Zone B', 'OCCUPIED');
SELECT * FROM parking_spaces;
UPDATE parking_spaces SET status = 'AVAILABLE' WHERE id = 1;