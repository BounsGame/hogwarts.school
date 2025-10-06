CREATE TABLE human (
id INT PRIMARY KEY,
carId INT,
name VARCHAR,
age INT,
license BOOLEAN,
FOREIGN KEY (carId) REFERENCES car(id),
);

CREATE TABLE car (
id INT PRIMARY KEY,
mark VARCHAR,
value INT,
model VARCHAR,
);