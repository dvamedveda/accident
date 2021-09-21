-- Создание таблицы статей.
create table rules
(
    id   serial primary key,
    name text
);

-- Заполнение таблицы статей.
insert into rules (name)
values ('Статья 1 Дорожного Кодекса РФ'),
       ('Статья 2 Дорожного Кодекса РФ'),
       ('Статья 3 Дорожного Кодекса РФ'),
       ('Статья 1 Уголовного Кодекса РФ'),
       ('Статья 2 Уголовного Кодекса РФ'),
       ('Статья 3 Уголовного Кодекса РФ');

-- Создание таблицы типов нарушений.
create table types
(
    id   serial primary key,
    name text
);

-- Заполнение таблицы типов нарушений.
insert into types (name)
values ('Столкновение машины с другой машиной'),
       ('Столкновение машины с пешеходом'),
       ('Столкновение машины с неподвижным объектом'),
       ('Столкновение машины с легким транспортом'),
       ('Столкновение легкого транспорта с другим легким транспортом'),
       ('Столкновение легкого транспорта с пешеходом'),
       ('Столкновение легкого транспорта с неподвижным объектом'),
       ('Легкий транспорт аварийно съехал с дороги'),
       ('Машина аварийно съехала с дороги');

-- Создание таблицы инцидентов.
create table accidents
(
    id            serial primary key,
    name          text,
    text          text,
    address       text,
    type          integer references types (id) not null,
    car_number    text,
    photo         bytea,
    encoded_photo text
);

-- Создание промежуточной таблицы для отношения accident many-to-many rules
create table accident_rules
(
    id serial primary key,
    accident integer references accidents (id),
    rule     integer references rules (id)
);

-- Создание таблицы разрешений для пользователей приложения.
create table authorities
(
    id serial primary key,
    authority varchar(50) not null unique
);

-- Создание таблицы пользователей приложения
create table users
(
    id serial primary key,
    username varchar(50) not null unique,
    password text not null,
    enabled boolean default true,
    authority_id int not null references authorities(id)
);

-- Заполнение таблицы начальным набором ролей.
insert into authorities(authority)
values ('ROLE_USER'), ('ROLE_ADMIN');

-- Создание дефолтного юзера в приложении.
insert into users (username, password, enabled, authority_id)
values ('root', '$2a$10$tvhNNL23MiuONVB9qjkH7uff0YhhkUGG7IAQ.5Egu2fPApxyihJdS', true,
        (select id from authorities where authority = 'ROLE_ADMIN'));
