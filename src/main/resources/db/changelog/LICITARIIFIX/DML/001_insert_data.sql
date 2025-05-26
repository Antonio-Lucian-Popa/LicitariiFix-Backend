--liquibase formatted sql

--changeset licitarifix:data-v1

-- Utilizatori
INSERT INTO users (id, keycloak_id, first_name, last_name, email, role)
VALUES
  ('11111111-1111-1111-1111-111111111111', 'keycloak-1', 'Ion', 'Popescu', 'ion@example.com', 'CLIENT'),
  ('22222222-2222-2222-2222-222222222222', 'keycloak-2', 'Maria', 'Ionescu', 'maria@example.com', 'MESERIAS'),
  ('33333333-3333-3333-3333-333333333333', 'keycloak-3', 'Admin', 'Root', 'admin@example.com', 'ADMIN');

-- Lucrări
INSERT INTO work_requests (id, title, description, location, status, client_id)
VALUES
  ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'Montare gresie', 'Am nevoie de montare gresie într-o baie de 10mp.', 'Cluj-Napoca', 'PENDING', '11111111-1111-1111-1111-111111111111'),
  ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 'Instalare centrală', 'Trebuie instalată o centrală termică.', 'București', 'APPROVED', '11111111-1111-1111-1111-111111111111');

-- Poze pentru lucrări
INSERT INTO work_images (id, work_request_id, photo_url)
VALUES
  ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbb1', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1', '/uploads/images/demo1.jpg'),
  ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbb2', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2', '/uploads/images/demo2.jpg');

-- Oferte
INSERT INTO offers (id, price, message, work_request_id, meserias_id)
VALUES
  ('cccccccc-cccc-cccc-cccc-ccccccccccc1', 1500.00, 'Pot începe săptămâna viitoare.', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2', '22222222-2222-2222-2222-222222222222');

-- Notificări demo
INSERT INTO notifications (id, user_id, message)
VALUES
  ('dddddddd-dddd-dddd-dddd-ddddddddddd1', '33333333-3333-3333-3333-333333333333', 'Ai o lucrare în așteptare de aprobare.'),
  ('dddddddd-dddd-dddd-dddd-ddddddddddd2', '11111111-1111-1111-1111-111111111111', 'Ai primit o ofertă nouă.');
