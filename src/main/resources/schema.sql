CREATE TABLE users (
    userId serial,
    username varchar(255),
    password varchar(255),
    personalName varchar(255),
    emailAddress varchar(255)
);

CREATE TABLE activity (
    activityId serial,
    userId integer,
    name varchar(255),
    description text
);

CREATE TABLE workitem (
    workItemId serial,
    activityId integer,
    start timestamp,
    stop timestamp,
    comment text,
    active boolean,
    archived boolean
);



insert into users (username, password, personalname, emailaddress) values ('talios', 'password', 'Mark Derricutt', 'mark@talios.com');

-- alter table workitem add column archived boolean;

-- jdbc.driver=jdbc:postgresql:tasktimes
-- jdbc.user=amrk
-- jdbc.password=

-- smtp.from.email=talios@gmail.com
-- smtp.from.personal=Mark Derricutt
-- smtp.host=smtp.woosh.co.nz
