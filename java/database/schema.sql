BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS seq_user_id;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;

CREATE TABLE author (
	author_id serial PRIMARY KEY,
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL
);

CREATE TABLE genre (
	genre_id serial PRIMARY KEY,
	genre_name varchar(50) NOT NULL
);

CREATE TABLE book (
	isbn varchar(13) PRIMARY KEY,
	title varchar(100) NOT NULL,
);

CREATE TABLE author_book (
	author_id int NOT NULL,
	isbn int NOT NULL,

	CONSTRAINT fk_author_id FOREIGN KEY (author_id) REFERENCES author (author_id),
	CONSTRAINT fk_isbn FOREIGN KEY (isbn) REFERENCES book (isbn)
);

CREATE TABLE genre_book (
	genre_id int NOT NULL,
	isbn int NOT NULL,

	CONSTRAINT fk_genre_id FOREIGN KEY (genre_id) REFERENCES genre (genre_id),
	CONSTRAINT fk_isbn FOREIGN KEY (isbn) REFERENCES book (isbn)
);

CREATE TABLE users (
	user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
	username varchar(50) NOT NULL UNIQUE,
	first_name varchar(50),
	last_name varchar(50),
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE user_book (
	user_id int NOT NULL,
	isbn int NOT NULL,
	minutes int NOT NULL DEFAULT(0),
	completed boolean NOT NULL DEFAULT(false),

	CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (user_id),
	CONSTRAINT fk_isbn FOREIGN KEY (isbn) REFERENCES book (isbn),
	CONSTRAINT chk_minutes_positive CHECK (minutes >= 0)
);

CREATE TABLE reading_activity (
	record_id serial PRIMARY KEY,
	isbn int NOT NULL,
	date DATE NOT NULL,
	reader NOT NULL,
	format varchar(50),
	minutes int NOT NULL DEFAULT(0),
	notes varchar(400),

	CONSTRAINT fk_isbn FOREIGN KEY (isbn) REFERENCES book (isbn),
	CONSTRAINT fk_reader FOREIGN KEY (reader) REFERENCES users (user_id),
	CONSTRAINT chk_minutes_positive CHECK (minutes >= 0),
	CONSTRAINT chk_date_not_future CHECK (date <= CURRENT_DATE)
);

CREATE TABLE family_account (
	family_id serial PRIMARY KEY,
	family_name varchar(50)
);

CREATE TABLE family_users (
	family_id int NOT NULL,
	user_id int NOT NULL

	CONSTRAINT fk_family_id FOREIGN KEY (family_id) REFERENCES family_account (family_id),
	CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE prizes (
	prize_id serial PRIMARY KEY,
	family_id int NOT NULL,
	prize_name varchar(50),
	description varchar(400),
	milestone int DEFAULT(0),
	user_group varchar(20),
	max_prizes int DEFAULT(1),
	start_date DATE,
	end_date DATE,

	CONSTRAINT fk_family_id FOREIGN KEY (family_id) REFERENCES family_account (family_id),
	CONSTRAINT chk_end_date_after_start_date CHECK (start_date <= end_date),
	CONSTRAINT chk_valid_role CHECK (user_group IN ('parent', 'child', 'both'))
);

CREATE TABLE user_prizes (
	user_id int NOT NULL,
	prize_id int NOT NULL,

	CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (user_id),
	CONSTRAINT fk_prize_id FOREIGN KEY (prize_id) REFERENCES prizes (prize_id)
);

CREATE TABLE friends(
	user_1 int NOT NULL,
	user_2 int NOT NULL,

	CONSTRAINT chk_not_same_user CHECK (user_1 != user_2)
);

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');


COMMIT TRANSACTION;
