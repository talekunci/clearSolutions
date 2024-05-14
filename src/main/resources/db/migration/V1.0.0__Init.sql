CREATE TABLE users(
    email VARCHAR(50) NOT NULL,
    firstname VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    birthdate DATE NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(255)
);

INSERT INTO users(email, firstname, lastname, birthdate, address) VALUES ('bush@whitehouse.gov', 'George', 'Bush', '1924-12-06', 'the WhiteHouse');
INSERT INTO users(email, firstname, lastname, birthdate, address, phone) VALUES ('bushjr@whitehouse.gov', 'George', 'Bush', '1946-06-07', 'the WhiteHouse', '+123456789');
INSERT INTO users(email, firstname, lastname, birthdate) VALUES ('google@gmail.com', 'Alfred', 'Nobel', '1833-12-10');