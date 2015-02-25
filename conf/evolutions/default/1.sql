# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "knol" ("Fname" VARCHAR(254) NOT NULL,"Lname" VARCHAR(254) NOT NULL,"Email" VARCHAR(254) NOT NULL,"Mobile" VARCHAR(254) NOT NULL,"id" SERIAL NOT NULL PRIMARY KEY);

# --- !Downs

drop table "knol";

