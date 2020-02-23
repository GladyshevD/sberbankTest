package ru.sberbank.onlinetest.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.onlinetest.model.User;
import ru.sberbank.onlinetest.model.UserAnswer;
import ru.sberbank.onlinetest.service.AnswerService;
import ru.sberbank.onlinetest.to.AnswerTo;

import java.util.List;

@RestController
@RequestMapping("/user")
public class AnswerController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final AnswerService service;

    @Autowired
    public AnswerController(AnswerService service) {
        this.service = service;
    }

    @PostMapping(value = "/answers", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createAnswers(@AuthenticationPrincipal User user, @RequestBody List<AnswerTo> answerTos) {
        log.debug("Post list of answers for user {}", user);
        service.create(user.getId(), answerTos);
    }

    @GetMapping(value = "/answers", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserAnswer> getAll(@AuthenticationPrincipal User user) {
        log.debug("Get all answers for User: {}", user);
        return service.getAll(user.getId());
    }
}
