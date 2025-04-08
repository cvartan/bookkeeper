-- CREATE SCHEMA bk AUTHORIZATION bk;

-- DROP SEQUENCE bk.authors_seq;

CREATE SEQUENCE bk.authors_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE bk.books_seq;

CREATE SEQUENCE bk.books_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE bk.orders_seq;

CREATE SEQUENCE bk.orders_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE bk.publishers_seq;

CREATE SEQUENCE bk.publishers_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE bk.readers_seq;

CREATE SEQUENCE bk.readers_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE bk.users_seq;

CREATE SEQUENCE bk.users_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;-- bk.authors definition

-- Drop table

-- DROP TABLE bk.authors;

CREATE TABLE bk.authors (
	id int8 DEFAULT nextval('authors_seq'::regclass) NOT NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT authors_pk PRIMARY KEY (id)
);
CREATE INDEX authors_name_idx ON bk.authors USING btree (name);


-- bk.publishers definition

-- Drop table

-- DROP TABLE bk.publishers;

CREATE TABLE bk.publishers (
	id int8 DEFAULT nextval('publishers_seq'::regclass) NOT NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT publishers_pk PRIMARY KEY (id)
);
CREATE INDEX publishers_name_idx ON bk.publishers USING btree (name);


-- bk.readers definition

-- Drop table

-- DROP TABLE bk.readers;

CREATE TABLE bk.readers (
	id int8 DEFAULT nextval('readers_seq'::regclass) NOT NULL,
	code varchar(32) NOT NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT readers_pk PRIMARY KEY (id),
	CONSTRAINT readers_unique UNIQUE (code)
);
CREATE INDEX readers_name_idx ON bk.readers USING btree (name);


-- bk.users definition

-- Drop table

-- DROP TABLE bk.users;

CREATE TABLE bk.users (
	id int8 DEFAULT nextval('users_seq'::regclass) NOT NULL,
	"name" varchar(255) NOT NULL,
	login varchar(255) NOT NULL,
	pass_hash varchar NOT NULL,
	is_blocked bool DEFAULT false NOT NULL,
	CONSTRAINT users_pk PRIMARY KEY (id),
	CONSTRAINT users_unique UNIQUE (login)
);


-- bk.books definition

-- Drop table

-- DROP TABLE bk.books;

CREATE TABLE bk.books (
	id int8 DEFAULT nextval('books_seq'::regclass) NOT NULL,
	isbn varchar(13) NOT NULL,
	title varchar(255) NOT NULL,
	author varchar(255) NULL,
	title_rus varchar(255) NULL,
	publisher varchar(255) NULL,
	publish_date date NOT NULL,
	author_id int8 NOT NULL,
	publisher_id int8 NOT NULL,
	CONSTRAINT books_isbn UNIQUE (isbn),
	CONSTRAINT books_pk PRIMARY KEY (id),
	CONSTRAINT books_authors_fk FOREIGN KEY (author_id) REFERENCES bk.authors(id),
	CONSTRAINT books_publishers_fk FOREIGN KEY (publisher_id) REFERENCES bk.publishers(id)
);


-- bk.orders definition

-- Drop table

-- DROP TABLE bk.orders;

CREATE TABLE bk.orders (
	id int8 DEFAULT nextval('orders_seq'::regclass) NOT NULL,
	code uuid NOT NULL,
	reader_id int8 NOT NULL,
	is_completed bool DEFAULT false NOT NULL,
	book_id int8 NOT NULL,
	CONSTRAINT orders_pk PRIMARY KEY (id),
	CONSTRAINT orders_unique UNIQUE (code),
	CONSTRAINT orders_books_fk FOREIGN KEY (book_id) REFERENCES bk.books(id),
	CONSTRAINT orders_readers_fk FOREIGN KEY (reader_id) REFERENCES bk.readers(id)
);
CREATE INDEX orders_code_idx ON bk.orders USING btree (code, is_completed);


-- bk.user_privilegies definition

-- Drop table

-- DROP TABLE bk.user_privilegies;

CREATE TABLE bk.user_privilegies (
	user_id int8 NOT NULL,
	privilege_name varchar(32) NOT NULL,
	CONSTRAINT user_privilegies_unique UNIQUE (user_id, privilege_name),
	CONSTRAINT user_privilegies_users_fk FOREIGN KEY (user_id) REFERENCES bk.users(id)
);