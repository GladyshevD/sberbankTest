package ru.sberbank.onlinetest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.onlinetest.model.CorrectAnswer;
import ru.sberbank.onlinetest.model.Question;
import ru.sberbank.onlinetest.model.UserAnswer;
import ru.sberbank.onlinetest.repository.AnswerRepository;
import ru.sberbank.onlinetest.repository.CorrectAnswerRepository;
import ru.sberbank.onlinetest.repository.QuestionRepository;
import ru.sberbank.onlinetest.to.AnswerTo;

import java.util.List;
import java.util.Map;

import static ru.sberbank.onlinetest.util.AnswerUtil.checkQuestionsExist;
import static ru.sberbank.onlinetest.util.AnswerUtil.getMapIsCorrect;

@Service
public class AnswerService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final CorrectAnswerRepository correctAnswerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository, CorrectAnswerRepository correctAnswerRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.correctAnswerRepository = correctAnswerRepository;
    }

    public void create(Integer userId, List<AnswerTo> answersList) {
        log.debug("Checking and creating answers for User {}", userId);
        List<Question> questionList = questionRepository.getAll();
        List<CorrectAnswer> correctAnswerList = correctAnswerRepository.getAll();
        if (checkQuestionsExist(answersList, questionList)) {
            Map<AnswerTo, Boolean> checkCorrect = getMapIsCorrect(correctAnswerList, answersList);
            answerRepository.save(checkCorrect, userId);
        }
    }

    public List<UserAnswer> getAll(Integer userId) {
        log.debug("Get all answers for User {}", userId);
        return answerRepository.getAll(userId);
    }
}
