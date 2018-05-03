ALTER TABLE clients
  ADD age smallint;

INSERT INTO clients (name, phone, age) VALUES
  ('WithAge', '100000000004', 22);