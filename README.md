## Test task for Sberbank

This is Rest application for ONLINE TEST.

There you can read description of the task - [TASK](https://github.com/GladyshevD/sberbankTest/blob/master/TEST.md)

Technology Stack used:
* JAVA 11, Maven, Intellij IDEA, Lombok
* Spring (Boot, Security, JDBC, Validation)
* H2 inmemory
* REST API (Json)

To run Application please run OnlineTestApplication.class

Users with roles hardcoded in db for curl requests:
* Username - `User`, password - `password`, email - `user@mail.ru`, roles - user
* Username - `Admin`, password - `admin`, email - `admin@mail.ru`, roles - admin, user

2 Type questions hardcoded with options:
* Укажите год рождения Сбербанка (1 type)

`1831` `1839` `1841` `1858`
* Кто на сегодняшний момент является главным акционером Сбербанка РФ (1 type)

`Министерство Финансов РФ` `Центральный банк РФ` `Правительство РФ` `Банк России`
* Назовите крупнейший банк в России, Центральной и Восточной Европе (1 type)

`Банк России` `Газпромбанк` `Альфа-Банк` `Сбербанк`

* Напишите фамилию председателя правления Сбербанка РФ (2 type)
* Напишите название формы правления в Сбербанке РФ (сокращенно) (2 type)

For every type of questions there is one entity with Set of options. If options are empty - it is 2 type of question
and server operate with them on such business logic.

Answers hardcoded:

`1841` `Центральный банк РФ` `Сбербанк` `Греф` `ПАО`

### CURL requests:

#### User block
- Register new user (if username or email exist, server will throw json with such message). Can register all not authenticated.

`curl -s -X POST -d '{"username":"newUser","email":"newUser@mail.ru","password":"newPassword","name":"Viktor"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/register`

- Get list of questions to answer (only authenticated)

`curl -s http://localhost:8080/user/list --user User:password`

- Answer questions by user (only authenticated, if at least 1 question is answered early, answers will not write to db
and server throw json with such message)

`curl -s -X POST -d '[{"questionId":"100002","answer":"1858"},{"questionId":"100003","answer":"Банк России"},{"questionId":"100004","answer":"Сбербанк"},{"questionId":"100005","answer":"Греф"},{"questionId":"100006","answer":"ЗАО"}]' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/user/answers --user User:password`

- Get answers by user

`curl -s http://localhost:8080/user/answers --user User:password`

- User get his statistics for answered questions (if only 1 user answered questions, statistics is not needed and such message in json
will throw by server. But individual user correct answers percentage will be shown in exception message. In statistics/** there are 
beans with descriptions of every indicator in statistics commented)

`curl -s http://localhost:8080/user/cabinet --user User:password`

#### Admin block

- Create new question (for creating 2 type of answer options must be null)

`curl -s -X POST -d '{"questionItem":"Кто из императоров учредил Сбербанк?","options":[{"optionItem": "Павел II"},{"optionItem": "Николай I"},{"optionItem": "Екатерина II"},{"optionItem": "Александр III"}]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/admin/questions/add --user Admin:admin`

- Get all users registered in system

`curl -s http://localhost:8080/admin/users --user Admin:admin`

- Get all user statistics (In statistics/** there are beans with descriptions of every indicator in statistics commented)

`curl -s http://localhost:8080/admin/cabinet --user Admin:admin`
