package ru.sberbank.onlinetest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserAnswer extends AbstractBaseEntity {
    private User user;

    @JsonIgnore
    private Question question;
    private String userAnswer;
    private boolean isCorrect;
}
