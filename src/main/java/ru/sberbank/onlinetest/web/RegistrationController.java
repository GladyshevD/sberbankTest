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
import ru.sberbank.onlinetest.model.User;
import ru.sberbank.onlinetest.service.UserService;
import ru.sberbank.onlinetest.to.UserTo;

import java.net.URI;

import static ru.sberbank.onlinetest.util.UserUtil.createNewFromTo;

@RestController
@RequestMapping("/register")
public class RegistrationController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService service;

    @Autowired
    public RegistrationController(UserService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<User> register(@Validated @RequestBody UserTo userTo) {
        log.debug("Create user from {}", userTo);
        User createdUser = service.create(createNewFromTo(userTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("register/{id}")
                .buildAndExpand(createdUser.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(createdUser);
    }
}