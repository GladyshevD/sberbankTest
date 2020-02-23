package ru.sberbank.onlinetest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CorrectAnswer extends AbstractBaseEntity {
    private Question question;
    private String answer;
}
