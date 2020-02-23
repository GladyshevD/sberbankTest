package ru.sberbank.onlinetest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserAnswer extends AbstractBaseEntity {

    private User user;
    private Question question;
    private String userAnswer;
    private boolean isCorrect;
}
