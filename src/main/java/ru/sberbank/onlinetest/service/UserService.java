package ru.sberbank.onlinetest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sberbank.onlinetest.model.User;
import ru.sberbank.onlinetest.model.statistics.AdminCabinet;
import ru.sberbank.onlinetest.repository.StatisticsRepository;
import ru.sberbank.onlinetest.repository.UserRepository;

import java.util.List;

import static ru.sberbank.onlinetest.util.UserUtil.prepareToSave;

@Service
public class UserService implements UserDetailsService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final StatisticsRepository statisticsRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository UserRepository, StatisticsRepository statisticsRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = UserRepository;
        this.statisticsRepository = statisticsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Get User by username: {}", username);
        User user = userRepository.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь " + username + " не найден");
        }
        return user;
    }

    public User create(User user) {
        return prepareAndSave(user);
    }

    private User prepareAndSave(User user) {
        return userRepository.save(prepareToSave(user, passwordEncoder));
    }

    public List<User> getAll() {
        log.info("Get All Users");
        return userRepository.getAll();
    }

    public AdminCabinet getStatistics() {
        return statisticsRepository.getAllStatistics();
    }
}
