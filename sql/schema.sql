USE rsvpdb;

CREATE TABLE rsvp 
(
	id int auto_increment not null,
	name varchar(64) not null,
    email varchar(64) not null,
    phone varchar(30),
    confirmation_date datetime,
    comments longtext,
    primary key(id)
);

desc rsvp;