MERGE INTO rating(rating_id,rating_name)
    VALUES (1,'G'),
    (2,'PG'),
    (3,'PG-13'),
    (4,'R'),
    (5,'NC-17');
MERGE INTO genre(genre_id,genre_name)
    VALUES (1, 'Комедия'),
    (2, 'Драма'),
    (3, 'Мультфильм'),
    (4, 'Триллер'),
    (5, 'Документальный'),
    (6, 'Боевик');


INSERT INTO users (login, user_name, email, birthday)
VALUES ('dm1366', 'Dmitriy', 'dm@ya.ru', '2016-11-10');

INSERT INTO users (login, user_name, email, birthday)
VALUES ('andry1855', 'Andrey', 'andr@ya.ru', '2010-12-10');

INSERT INTO users (login, user_name, email, birthday)
VALUES ('mak333', 'Mackar', 'mac333@ya.ru', '208-10-10');

INSERT INTO users (login, user_name, email, birthday)
VALUES ('Dublicate', 'User', 'mail@.ru', '1990-01-21');


INSERT INTO film (name, description, release_date, duration, rating_id)
VALUES ('Безудержное веселье', 'мимолетная комедия', '2020-02-22', 436, 1);

INSERT INTO film (name, description, release_date, duration, rating_id)
VALUES ('Катастрофа', 'фильм кфтастрофа', '2022-06-18', 120, 3);

INSERT INTO film (name, description, release_date, duration, rating_id)
VALUES ('Красная синева', 'Закат синел красным в гранатовой лазури аквамарина', '2016-08-16', 122, 5);