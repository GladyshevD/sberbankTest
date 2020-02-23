package ru.sberbank.onlinetest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.onlinetest.model.Question;
import ru.sberbank.onlinetest.repository.QuestionRepository;

import java.util.List;

@Service
public class QuestionService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final QuestionRepository repository;

    @Autowired
    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    public List<Question> getAll() {
        log.debug("Get all questions");
        return repository.getAll();
    }

    public Question create(Question question) {
        log.debug("Creating new question from {}", question);
        return repository.save(question);
    }
}
