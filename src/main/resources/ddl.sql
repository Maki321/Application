create table CarBrand
(
    Id   int auto_increment
        primary key,
    Name varchar(50) not null
);

create table CarModel
(
    Id         int auto_increment
        primary key,
    Name       varchar(50) not null,
    CarBrandId int         not null,
    constraint CarModel_CarBrand_Id_fk
        foreign key (CarBrandId) references CarBrand (Id)
);

create table CarPart
(
    Id              int auto_increment
        primary key,
    Name            varchar(100) not null,
    ManufactureDate date         not null,
    SerialNumber    varchar(50)  not null,
    constraint CarPart_SerialNumber_uindex
        unique (SerialNumber)
);

create table CarPartModel
(
    Id      int auto_increment
        primary key,
    ModelId int not null,
    PartId  int not null,
    constraint CarPartModel_CarModel_Id_fk
        foreign key (ModelId) references CarModel (Id),
    constraint CarPartModel_CarPart_Id_fk
        foreign key (PartId) references CarPart (Id)
);

