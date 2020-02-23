package ru.sberbank.onlinetest.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ApplicationException extends RuntimeException {

    @Getter
    @Setter
    private String message;
}
