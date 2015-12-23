DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, dateTime, description, calories)
VALUES (100000, '2015-12-23', 'breakfast', 400);

INSERT INTO meals (user_id, dateTime, description, calories)
VALUES (100000, '2015-12-23', 'lunch', 1400);

INSERT INTO meals (user_id, dateTime, description, calories)
VALUES (100000, '2015-12-23', 'dinner', 2400);

INSERT INTO meals (user_id, dateTime, description, calories)
VALUES (100000, '2015-12-21', 'breakfast', 300);

INSERT INTO meals (user_id, dateTime, description, calories)
VALUES (100000, '2015-12-21', 'lunch', 1200);

INSERT INTO meals (user_id, dateTime, description, calories)
VALUES (100000, '2015-12-21', 'dinner', 1400);



INSERT INTO meals (user_id, dateTime, description, calories)
VALUES (100001, '2015-12-23', 'breakfast', 400);

INSERT INTO meals (user_id, dateTime, description, calories)
VALUES (100001, '2015-12-23', 'lunch', 1400);

INSERT INTO meals (user_id, dateTime, description, calories)
VALUES (100001, '2015-12-23', 'dinner', 2400);