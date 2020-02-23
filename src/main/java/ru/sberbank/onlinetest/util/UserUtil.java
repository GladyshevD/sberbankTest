package ru.sberbank.onlinetest.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import ru.sberbank.onlinetest.model.Role;
import ru.sberbank.onlinetest.model.User;
import ru.sberbank.onlinetest.to.UserTo;

public class UserUtil {

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getUsername(), userTo.getEmail(),
                userTo.getPassword(), userTo.getName(), Role.USER);
    }

    public static User prepareToSave(User user, PasswordEncoder passwordEncoder) {
        String password = user.getPassword();
        user.setPassword(StringUtils.hasText(password) ? passwordEncoder.encode(password) : password);
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}
