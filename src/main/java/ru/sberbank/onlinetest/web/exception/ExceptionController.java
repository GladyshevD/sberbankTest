package ru.sberbank.onlinetest.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.sberbank.onlinetest.util.exception.ApplicationException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ApplicationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleExceptions(ApplicationException ae) {
        return new ExceptionResponse(ae.getMessage());
    }
}