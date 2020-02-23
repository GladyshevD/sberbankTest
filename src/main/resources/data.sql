DELETE FROM User_Answer;
DELETE FROM Correct_Answer;
DELETE FROM Option;
DELETE FROM Question;
DELETE FROM Role;
DELETE FROM User;

ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO USER (USERNAME, EMAIL, PASSWORD, NAME)
VALUES ('User', 'user@mail.ru', '$2y$10$9prVLrzmkpXh4l7rcluUNu/IFlaHU4DqXw3KtVGtmBeqAGApx6Ldi', 'Петров Александр'),
       ('Admin', 'admin@mail.ru', '$2y$10$X8HKKJM631WcUscyBr.85eFuaZ4Mo5uTQ32Ipx1fKi/KWG9BrEcDy', 'Иванов Алексей');

INSERT INTO ROLE (USER_ID, ROLE)
VALUES (100000, 'USER'),
       (100001, 'ADMIN'),
       (100001, 'USER');

INSERT INTO QUESTION (QUESTION_ITEM)
VALUES ('Укажите год рождения Сбербанка:'),
       ('Кто на сегодняшний момент является главным акционером Сбербанка РФ:'),
       ('Назовите крупнейший банк в России, Центральной и Восточной Европе:'),
       ('Напишите фамилию председателя правления Сбербанка РФ:'),
       ('Напишите название формы правления в Сбербанке РФ (сокращенно):');

INSERT INTO OPTION (QUESTION_ID, OPTION_ITEM)
VALUES (100002, '1831'),
       (100002, '1839'),
       (100002, '1841'),
       (100002, '1858'),
       (100003, 'Министерство Финансов РФ'),
       (100003, 'Центральный банк РФ'),
       (100003, 'Правительство РФ'),
       (100003, 'Банк России'),
       (100004, 'Банк России'),
       (100004, 'Газпромбанк'),
       (100004, 'Альфа-Банк'),
       (100004, 'Сбербанк');


INSERT INTO CORRECT_ANSWER (QUESTION_ID, ANSWER)
VALUES (100002, '1841'),
       (100003, 'Центральный банк РФ'),
       (100004, 'Сбербанк'),
       (100005, 'Греф'),
       (100006, 'ПАО');