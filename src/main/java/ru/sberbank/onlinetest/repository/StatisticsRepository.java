package ru.sberbank.onlinetest.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.onlinetest.model.statistics.AdminCabinet;
import ru.sberbank.onlinetest.model.statistics.UserCabinet;
import ru.sberbank.onlinetest.util.exception.NotMuchUsersException;

import java.util.HashMap;
import java.util.Map;

@Repository
@Transactional(readOnly = true)
public class StatisticsRepository {

    private static final String NUM_USERS = "SELECT COUNT(ID) FROM USER";
    private static final String USERS_TESTED = "SELECT COUNT(DISTINCT USER_ID) FROM USER_ANSWER";
    private static final String USERS_ANSWERED_ALL = "SELECT COUNT(DISTINCT USER_ID) FROM USER_ANSWER " +
            "WHERE USER_ID NOT IN (SELECT USER_ID FROM USER_ANSWER WHERE ANSWER IS NULL)";
    private static final String USERS_ANSWERED_ALL_CORRECT = "SELECT COUNT(DISTINCT USER_ID) FROM USER_ANSWER " +
            "WHERE USER_ID NOT IN (SELECT USER_ID FROM USER_ANSWER WHERE CORRECT IS FALSE)";
    private final JdbcTemplate jdbcTemplate;
    private static final String USER_RATE = "SELECT DISTINCT USER_ID, " +
            "ROUND(100*COUNT(CASE WHEN CORRECT=TRUE THEN 1 END)/CAST(COUNT(USER_ID) AS REAL), 2) " +
            "AS RATE FROM USER_ANSWER GROUP BY USER_ID";

    @Autowired
    public StatisticsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public AdminCabinet getAllStatistics() {
        Integer num = jdbcTemplate.queryForObject(NUM_USERS, Integer.class);
        Integer numTested = jdbcTemplate.queryForObject(USERS_TESTED, Integer.class);
        Integer allAnswered = jdbcTemplate.queryForObject(USERS_ANSWERED_ALL, Integer.class);
        Integer allCorrect = jdbcTemplate.queryForObject(USERS_ANSWERED_ALL_CORRECT, Integer.class);

        return new AdminCabinet(num, numTested, allAnswered, allCorrect);
    }

    //separate queries turned out too complicated so decided to get statistics for
    //every user and operate with it by java
    public UserCabinet getUserStatistics(Integer userId) {
        UserCabinet userCabinet = new UserCabinet();
        Map<Integer, Float> map = getRateMap();
        Float successRate = map.get(userId);
        userCabinet.setSuccessRate(successRate);
        int allUsers = map.values().size() - 1;

        try {
            Float worseRate = 100 * (float) (map.values().stream()
                    .filter(a -> a < successRate)
                    .count() / allUsers);
            userCabinet.setWorseRate(worseRate);

            Float betterRate = 100 * (float) (map.values().stream()
                    .filter(a -> a > successRate)
                    .count() / allUsers);
            userCabinet.setBetterRate(betterRate);
        } catch (ArithmeticException a) {
            throw new NotMuchUsersException("На вопросы пока ответил только один пользователь");
        }

        return userCabinet;
    }

    private Map<Integer, Float> getRateMap() {
        return jdbcTemplate.query(USER_RATE, rs -> {
            Map<Integer, Float> ratesMap = new HashMap<>();
            while (rs.next()) {
                ratesMap.put(rs.getInt("user_id"), rs.getFloat("rate"));
            }
            return ratesMap;
        });
    }
}