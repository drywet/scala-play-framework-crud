CREATE TABLE clients (
  id    bigserial    NOT NULL,
  name  varchar(100) NOT NULL,
  phone varchar(20),
  PRIMARY KEY (id)
);

INSERT INTO clients (name, phone) VALUES
  ('Bob', '100000000001'),
  ('Sam', '100000000002'),
  ('John', '100000000003');