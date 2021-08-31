create table member (
    id varchar(36),
    nama varchar(255) not null,
    no_ktp varchar (100) not null,
    file_ktp_mime_type varchar (100) not null,
    primary key (id)
);