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
    accident_id integer references accidents (id),
    rule_id     integer references rules (id)
);