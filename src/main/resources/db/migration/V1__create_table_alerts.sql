CREATE TABLE alerts (
  id             VARCHAR(36) PRIMARY KEY,
  cep            VARCHAR(9) NOT NULL,
  street_name    VARCHAR(255) NOT NULL,
  number         INTEGER NOT NULL,
  risk_level     VARCHAR(10) NOT NULL,
  recommendation TEXT NOT NULL,
  active         BOOLEAN NOT NULL DEFAULT TRUE,
  created_at     TIMESTAMP NOT NULL
);