-Register new user
'curl -s -X POST -d '{"username":"newUser","email":"newUser@mail.ru","password":"newPassword","name":"Viktor"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/register'

-Get list of questions to answer
'curl -s http://localhost:8080/user/list --user User:password'

-Get all users
'curl -s http://localhost:8080/admin/users --user Admin:admin'

-Create new question
'curl -s -X POST -d '{"questionItem":"Кто из императоров учредил Сбербанк?","options":[{"optionItem": "Павел II"},{"optionItem": "Николай I"},{"optionItem": "Екатерина II"},{"optionItem": "Александр III"}]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/admin/questions/add --user Admin:admin'

-Answer questions by user
'curl -s -X POST -d '[{"questionId":"100002","answer":"1858"},{"questionId":"100003","answer":"Банк России"},{"questionId":"100004","answer":"Сбербанк"},{"questionId":"100005","answer":"Греф"},{"questionId":"100006","answer":"ЗАО"}]' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/user/answers --user User:password'

-Get answers by user
'curl -s http://localhost:8080/user/answers --user User:user'