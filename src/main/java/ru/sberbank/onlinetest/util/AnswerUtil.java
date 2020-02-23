package ru.sberbank.onlinetest.util;

import ru.sberbank.onlinetest.model.CorrectAnswer;
import ru.sberbank.onlinetest.model.Option;
import ru.sberbank.onlinetest.model.Question;
import ru.sberbank.onlinetest.to.AnswerTo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AnswerUtil {
    public static boolean checkQuestionsExist(List<AnswerTo> answers, List<Question> questions) {

        //firstly we check if answers are for questions that exist
        //then we check if answers for questions with options are matching such options
        //if returning false we consider User cheat with answers and server will not accept his answers
        //even so some of them are tested
        Map<Integer, Question> questionMap = questions.stream()
                .collect(Collectors.toMap(Question::getId, Function.identity()));
        for (AnswerTo answerTo : answers) {
            Question q = questionMap.get(answerTo.getQuestionId());
            if (q != null) {
                Set<Option> options = q.getOptions();
                if (!options.isEmpty()) {
                    List<String> optionStrings = options.stream().
                            map(Option::getOptionItem)
                            .collect(Collectors.toList());
                    if (!optionStrings.contains(answerTo.getAnswer()))
                        return false;
                }
            } else return false;
        }
        return true;
    }

    public static Map<AnswerTo, Boolean> getMapIsCorrect(List<CorrectAnswer> correctAnswers, List<AnswerTo> answerTos) {
        Map<Integer, String> correctAnswerMap = correctAnswers.stream()
                .collect(Collectors.toMap(c -> c.getQuestion().getId(), CorrectAnswer::getAnswer));

        return answerTos.stream()
                .collect(Collectors.toMap(Function.identity(),
                        answerTo -> answerTo.getAnswer() != null &&
                                answerTo.getAnswer().equals(correctAnswerMap.get(answerTo.getQuestionId()))));
    }
}