# java-filmorate
Бэкенд социальной сети,для любителей фильмов.
### Реализованы следующие эндпоинты:

#### 1. Фильмы

* POST /films - создание фильма
* PUT /films - редактирование фильма
* GET /films - получение списка всех фильмов
* GET /films/{id} - получение информации о фильме по его id
* PUT /films/{id}/like/{userId} — поставить лайк фильму
* DELETE /films/{id}/like/{userId} — удалить лайк фильма
* GET /films/popular?count={count} — возвращает список из первых count фильмов по количеству лайков.
  Если значение параметра count не задано, возвращает первые 10.

#### 2. Пользователи

* POST /users - создание пользователя
* PUT /users - редактирование пользователя
* GET /users - получение списка всех пользователей
* GET /users/{id} - получение данных о пользователе по id
* PUT /users/{id}/friends/{friendId} — добавление в друзья
* DELETE /users/{id}/friends/{friendId} — удаление из друзей
* GET /users/{id}/friends — возвращает список друзей
* GET /users/{id}/friends/common/{otherId} — возвращает список друзей, общих с другим пользователем


Схема отображает отношения таблиц в базе данных:

* film - данные о фильмах (primary key - film_id, foreign key - rating_id,genre_id)
* genre - названия жанров фильма (primary key - genre_id)
* mpa_rating - определяет возрастное ограничение для фильма (primary key - rating_id)
* film_likes - информация о лайках фильма и кто их поставил (primary key -  film_id, foreign key - user_id)
* user - данные о пользователях (primary key - user_id)
* film_genre - связывает id фильма с id жанра(primary key - film_id)
* friends - содержит информации о статусе «дружбы» между двумя пользователями (primary key - id,foreign key - friend_id,status)
    * status = 1 — в таблице две записи о дружбе двух пользователей (id1 = id2; id2 = id1),
    * status = 2 — в таблице одна запись о дружбе двух пользователей(id1 = id2).
* friendship_status - содержит значение статуса дружбы по ключу 1 или 2
    
![](https://github.com/RakhmaIs/java-filmorate/blob/e6e14075fb3a7d99d4fdaca0ab9307f76f49d577/ER.png)

### Примеры запросов:
получение списка всех пользователей
```
SELECT *
FROM user
```
получение информации о пользователе по его id

```
SELECT *
FROM user
WHERE id = ?
```
получение информации о фильме по его id
```
SELECT f.*, mpt.rating_name, COUNT(flt.user_id) AS rate
FROM film AS f
LEFT JOIN mpa_rating AS mpt ON f.film_id = mpt.film_id
LEFT JOIN film_like AS fl ON f.film_id = fl.film_id
WHERE f.id = 2
GROUP BY f.id
```

