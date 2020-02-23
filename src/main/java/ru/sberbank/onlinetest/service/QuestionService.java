package ru.sberbank.onlinetest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.onlinetest.model.Question;
import ru.sberbank.onlinetest.model.statistics.UserCabinet;
import ru.sberbank.onlinetest.repository.QuestionRepository;
import ru.sberbank.onlinetest.repository.StatisticsRepository;

import java.util.List;

@Service
public class QuestionService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final QuestionRepository questionRepository;
    private final StatisticsRepository statisticsRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, StatisticsRepository statisticsRepository) {
        this.questionRepository = questionRepository;
        this.statisticsRepository = statisticsRepository;
    }

    public List<Question> getAll() {
        log.debug("Get all questions");
        return questionRepository.getAll();
    }

    public Question create(Question question) {
        log.debug("Creating new question from {}", question);
        return questionRepository.save(question);
    }

    public UserCabinet getStatistics(Integer userId) {
        log.debug("Getting statistics for {}", userId);
        return statisticsRepository.getUserStatistics(userId);
    }
}
