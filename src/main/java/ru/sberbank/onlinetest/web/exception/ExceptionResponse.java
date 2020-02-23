package ru.sberbank.onlinetest.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    @Getter
    @Setter
    private String errorMessage;
}