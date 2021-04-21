CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE SCHEMA IF NOT EXISTS store;

CREATE SEQUENCE IF NOT EXISTS hibernate_sequence
    INCREMENT 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 2
    CACHE 1;

ALTER TABLE hibernate_sequence
    OWNER TO postgres;

CREATE TABLE IF NOT EXISTS users
(
    username    VARCHAR(50)     UNIQUE NOT NULL PRIMARY KEY
        CONSTRAINT valid_username CHECK (char_length(ltrim(username)) > 2),
    password    TEXT
        CONSTRAINT valid_password CHECK (char_length(password) > 2),
    enabled     BOOLEAN         NOT NULL,
    firstName   VARCHAR(50)     NOT NULL,
    lastName    VARCHAR(50)     NOT NULL,
    email       VARCHAR(320)    NOT NULL,
    address     VARCHAR(320)    NOT NULL,
    accountType VARCHAR(8)      NOT NULL
        default 'customer'
        CONSTRAINT valid_account_type CHECK (lower(accountType) in ('customer', 'employee', 'manager'))
);

CREATE TABLE IF NOT EXISTS store.items
(
    uuid        BIGSERIAL       NOT NULL UNIQUE PRIMARY KEY,
    name        VARCHAR(50)     NOT NULL,
    description VARCHAR(512)    NOT NULL
        default 'WIP Description',
    quantity    integer         NOT NULL
        default 0
        CONSTRAINT non_negative_quantity CHECK (quantity >= 0),
    price       NUMERIC(12, 2)  NOT NULL
        CONSTRAINT positive_price CHECK (price > 0)
);

CREATE TABLE IF NOT EXISTS store.categories
(
    id          VARCHAR(50)     NOT NULL UNIQUE PRIMARY KEY,
    name        VARCHAR(100)    NOT NULL
);

CREATE TABLE IF NOT EXISTS store.item_categories
(
    id          BIGSERIAL       NOT NULL PRIMARY KEY
        REFERENCES store.items(uuid),
    category_id VARCHAR(50)     NOT NULL
        REFERENCES store.categories(id)
);

CREATE TABLE IF NOT EXISTS store.saved_orders
(
  id            BIGSERIAL       NOT NULL UNIQUE PRIMARY KEY,
  username      VARCHAR(50)     NOT NULL
      REFERENCES users (username),
  item_id       integer         NOT NULL
      REFERENCES store.items(uuid),
  quantity      integer         NOT NULL
      default 1
      CONSTRAINT positive_quantity CHECK (quantity > 0)
);

CREATE TABLE IF NOT EXISTS store.pending_orders
(
  id            BIGSERIAL       NOT NULL UNIQUE PRIMARY KEY,
  username      VARCHAR(50)     NOT NULL
      REFERENCES users (username),
  order_id      BIGSERIAL       NOT NULL
      REFERENCES store.items(uuid)
);

