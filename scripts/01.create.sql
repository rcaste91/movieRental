create extension pgcrypto;

CREATE TABLE movie
(
    movie_id  SERIAL PRIMARY KEY,
    title   varchar(60) NOT NULL,
    description   text,
    stock        integer,
	rental_price numeric(8,2) NOT NULL,
	sale_price numeric(8,2) NOT NULL,
	availability varchar(2) NOT NULL
);

CREATE TABLE role
(
    id  SERIAL PRIMARY KEY,
    name   varchar(15) NOT NULL
);

CREATE TABLE movie_image
(
    image_id  SERIAL PRIMARY KEY,
    movie_id   integer NOT NULL REFERENCES movie (movie_id),
	movie_photo text   NULL
);

CREATE TABLE movie_log
(
    movie_log_id  SERIAL PRIMARY KEY,
    movie_id   integer NOT NULL,
    title   varchar(60) NOT NULL,
	rental_price numeric(8,2) NOT NULL,
	sale_price numeric(8,2) NOT NULL,
	change_date timestamp NOT NULL
);

CREATE TABLE users
(
    user_id  SERIAL PRIMARY KEY,
	role_id     integer NOT NULL REFERENCES role (id),
    name varchar(60) NOT NULL,
	username varchar(60) NOT NULL,
	email varchar(60)NOT NULL,
	password text NOT NULL
);


CREATE TABLE movie_like
(
    like_id  SERIAL PRIMARY KEY,
    user_id integer NOT NULL REFERENCES users (user_id),
	movie_id   integer NOT NULL REFERENCES movie (movie_id)
);

CREATE TABLE rent
(
    rent_id  SERIAL PRIMARY KEY,
    user_id integer NOT NULL REFERENCES users (user_id),
	movie_id   integer NOT NULL REFERENCES movie (movie_id),
	rent_date timestamp NOT NULL,
	return_date timestamp NOT NULL,
	user_return_date timestamp,
	subtotal numeric(8,2) NOT NULL,
	total numeric(8,2) NOT NULL
);

CREATE TABLE sale
(
    sale_id  SERIAL PRIMARY KEY,
    user_id integer NOT NULL REFERENCES users (user_id),
	movie_id   integer NOT NULL REFERENCES movie (movie_id),
	sale_date timestamp NOT NULL,
	quantity integer NOT NULL,
	total numeric(8,2) NOT NULL
);

CREATE TABLE constants
(
	constant_id  SERIAL PRIMARY KEY,
	name varchar(15),
	value_c  numeric(8,2)
);