-- Cria a extensão pgcrypto, se não existir
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Inserir roles
INSERT INTO roles (id, enum_roles)
VALUES 
  (gen_random_uuid(), 'ROLE_USER'), 
  (gen_random_uuid(), 'ROLE_ADMIN');
