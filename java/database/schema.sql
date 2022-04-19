BEGIN TRANSACTION;

DROP TABLE IF EXISTS  friends,user_prizes,prizes,family_account,reading_activity,user_book,users;
DROP SEQUENCE IF EXISTS seq_user_id;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;

CREATE TABLE family_account (
	family_id serial PRIMARY KEY,
	family_name varchar(50)
);

CREATE TABLE users (
	user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
	username varchar(50) NOT NULL UNIQUE,
	name varchar(50),
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,

	family_id int,
	CONSTRAINT PK_user PRIMARY KEY (user_id),
	CONSTRAINT fk_family_id FOREIGN KEY (family_id) REFERENCES family_account (family_id)

);

CREATE TABLE user_book (
	user_book_id serial PRIMARY KEY,
	user_id int NOT NULL,
	title varchar(100) NOT NULL,
	authors varchar(500),
	isbn varchar(13) NOT NULL,
	minutes int NOT NULL DEFAULT(0),
	completed boolean NOT NULL DEFAULT(false),
	favorited boolean DEFAULT(false),
	book_cover_url varchar(500),
	description varchar(2000),
	rating int,


	CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (user_id),
	CONSTRAINT chk_minutes_positive CHECK (minutes >= 0)
);

CREATE TABLE reading_activity (
	record_id serial PRIMARY KEY,
	user_book_id int NOT NULL,
	date DATE NOT NULL,
	reader int NOT NULL,
	format varchar(50),
	minutes int NOT NULL DEFAULT(0),
	notes varchar(400),

	CONSTRAINT fk_user_book_id FOREIGN KEY (user_book_id) REFERENCES user_book (user_book_id),
	CONSTRAINT fk_reader FOREIGN KEY (reader) REFERENCES users (user_id),
	CONSTRAINT chk_minutes_positive CHECK (minutes >= 0),
	CONSTRAINT chk_date_not_future CHECK (date <= CURRENT_DATE)
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
	CONSTRAINT chk_end_date_after_start_date CHECK (start_date <= end_date)

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

	CONSTRAINT chk_not_same_user CHECK (user_1 != user_2),
	CONSTRAINT fk_user_1 FOREIGN KEY (user_1) REFERENCES users (user_id),
	CONSTRAINT fk_user_2 FOREIGN KEY (user_2) REFERENCES users (user_id)
);

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');



COMMIT TRANSACTION;
