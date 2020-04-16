create database IPS;

create table booth(
	booth_id int(11) auto_increment primary key,
	information text
);

create table booth_info(
	info_id int(11) auto_increment primary key,
	booth_id int(11),
	title varchar(50),
	subtitle text,
	description text,
	FOREIGN KEY (booth_id) REFERENCES booth(booth_id)
)