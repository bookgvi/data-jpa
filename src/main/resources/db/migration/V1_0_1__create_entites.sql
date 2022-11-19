drop table if exists singer cascade;
drop table if exists album;
drop table if exists instrument cascade;
drop table if exists singer_instrument;

CREATE TABLE singer (
ID bigserial NOT null primary key,
first_name VARCHAR(60) NOT NULL,
last_name VARCHAR (60) NOT NULL,
VERSION bigint NOT NULL default 0,
birth_date DATE
);

CREATE TABLE album (
ID bigserial NOT null primary key,
singer_id BigInt NOT null,
title VARCHAR(100) NOT null,
release_date DATE,
VERSION bigint NOT NULL default 0,
CONSTRAINT FK_ALBUM_SINGER foreign key (singer_id) REFERENCES singer (ID)
);

CREATE TABLE instrument (
id bigint NOT null primary key,
title varchar(20) not null
);

CREATE TABLE singer_instrument (
id bigserial not null primary key,
singer_id bigint NOT NULL,
instrument_id bigint NOT NULL,
CONSTRAINT FK_SINGER_INSTRUMENT_1 FOREIGN key (singer_id) REFERENCES singer (ID) ON DELETE CASCADE,
CONSTRAINT FK_SINGER_INSTRUMENT_2 FOREIGN key (instrument_id) REFERENCES INSTRUMENT (id)
);