package ru.sberbank.onlinetest.util.exception;

public class UserAlreadyExistException extends ApplicationException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
