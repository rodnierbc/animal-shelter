SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS amimals (
  id int PRIMARY KEY auto_increment,
  name VARCHAR,
  gender BIT,
  admittanceDate TIMESTAMP,
  breedId INTEGER,
  typeId INTEGER
);

CREATE TABLE IF NOT EXISTS customers (
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
 phone VARCHAR,
 breedId INTEGER,
 typeId INTEGER

);
CREATE TABLE IF NOT EXISTS breeds (
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
 description VARCHAR,
 typeId INTEGER
);
CREATE TABLE IF NOT EXISTS types (
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
 description VARCHAR
);
