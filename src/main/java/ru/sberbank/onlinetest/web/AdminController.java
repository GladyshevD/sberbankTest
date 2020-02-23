package ru.sberbank.onlinetest.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.onlinetest.model.User;
import ru.sberbank.onlinetest.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        log.info("Get All Users");
        return userService.getAll();
    }
}
