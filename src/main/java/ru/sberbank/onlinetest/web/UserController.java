package ru.sberbank.onlinetest.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.onlinetest.model.Question;
import ru.sberbank.onlinetest.model.User;
import ru.sberbank.onlinetest.model.statistics.UserCabinet;
import ru.sberbank.onlinetest.service.QuestionService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final QuestionService service;

    @Autowired
    public UserController(QuestionService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public List<Question> getQuestions() {
        log.debug("Get all questions");
        return service.getAll();
    }

    @GetMapping(value = "/cabinet", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserCabinet getStatistics(@AuthenticationPrincipal User user) {
        log.debug("Get statistics for User {}", user);
        return service.getStatistics(user.getId());
    }
}
