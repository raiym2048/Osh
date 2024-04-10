create table activity (
    year integer,
    id bigserial not null,
    image_id bigint unique,
    description text, name varchar(255),
    primary key (id));

create table event (
    date_time timestamp(6),
    id bigserial not null,
    image_id bigint unique,
    description text,
    name varchar(255),
    primary key (id));

create table image (
    id bigserial not null,
    name varchar(255) unique,
    path varchar(255),
    primary key (id));

create table news (
    created_at timestamp(6),
    id bigserial not null,
    image_id bigint unique,
    description text,
    name varchar(255),
    primary key (id));

create table numbers (
    business_project_numbers integer,
    hub_numbers integer,
    new_work_place_numbers integer,
    people_numbers integer,
    project_numbers integer,
    social_project_numbers integer,
    id bigserial not null,
    primary key (id));

create table partners (
    id bigserial not null,
    image_id bigint unique,
    primary key (id));

create table project (
    id bigserial not null,
    description text,
    name varchar(255),
    subtopic varchar(255),
    primary key (id));

create table project_images (
    images_id bigint not null unique,
    project_id bigint not null);

create table role (
    id bigserial not null,
    name varchar(255) unique,
    primary key (id));

create table services (
    id bigserial not null,
    description text,
    name varchar(255),
    subtopic varchar(255),
    primary key (id));

create table services_images (
    images_id bigint not null unique,
    services_id bigint not null);

create table sponsorship (
    id bigserial not null,
    address varchar(255),
    bank varchar(255),
    bic varchar(255),
    company varchar(255),
    director varchar(255),
    inn varchar(255),
    payment_account varchar(255),
    primary key (id));

create table users (
    id bigserial not null,
    email varchar(255),
    password varchar(255),
    role_name varchar(255),
    username varchar(255),
    primary key (id));

create table volunteer (
    date_of_birth date,
    is_confirmed boolean not null,
    id bigserial not null,
    comment varchar(255),
    contacts varchar(255),
    gender varchar(255) check (gender in ('MALE','FEMALE')),
    name varchar(255), town varchar(255),
    primary key (id));





alter table if exists activity
    add constraint activity_image_fk
        foreign key (image_id) references image;

alter table if exists event
    add constraint event_image_fk
        foreign key (image_id) references image;

alter table if exists news
    add constraint news_image_fk
        foreign key (image_id) references image;

 alter table if exists partners
     add constraint partners_image_fk
         foreign key (image_id) references image;

alter table if exists project_images
    add constraint project_images_image_fk
        foreign key (images_id) references image;

alter table if exists project_images
    add constraint project_images_project_fk
        foreign key (project_id) references project;

alter table if exists services_images
    add constraint services_images_image_fk
        foreign key (images_id) references image;

alter table if exists services_images
    add constraint services_images_services_fk
        foreign key (services_id) references services;

alter table if exists users
    add constraint users_role_fk
        foreign key (role_name) references role (name);