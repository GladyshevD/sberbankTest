package ru.sberbank.onlinetest.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.sberbank.onlinetest.model.Question;
import ru.sberbank.onlinetest.service.QuestionService;

import java.net.URI;

@RestController
@RequestMapping("/admin")
public class QuestionController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final QuestionService service;

    @Autowired
    public QuestionController(QuestionService service) {
        this.service = service;
    }

    @PostMapping(value = "/questions/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Question> createNew(@Validated @RequestBody Question question) {
        log.debug("Creating new question from {}", question);
        Question newQuestion = service.create(question);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("admin/add/{id}")
                .buildAndExpand(newQuestion.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(newQuestion);
    }
}
