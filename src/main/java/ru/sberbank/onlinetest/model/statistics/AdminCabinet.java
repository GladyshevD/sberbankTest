package ru.sberbank.onlinetest.model.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminCabinet {
    //registered users
    private Integer all;

    //users with answered questions
    private Integer tested;

    //users with all answered questions
    private Integer allAnswered;

    //users with all answered questions correctly
    private Integer allCorrect;
}
