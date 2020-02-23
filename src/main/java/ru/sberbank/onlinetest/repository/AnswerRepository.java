package ru.sberbank.onlinetest.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.onlinetest.model.Question;
import ru.sberbank.onlinetest.model.UserAnswer;
import ru.sberbank.onlinetest.to.AnswerTo;
import ru.sberbank.onlinetest.util.exception.UniqueIndexException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(readOnly = true)
public class AnswerRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert userAnswerInsert;

    @Autowired
    public AnswerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.userAnswerInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("User_Answer")
                .usingGeneratedKeyColumns("id");
    }

    public void save(Map<AnswerTo, Boolean> answers, Integer userId) {
        for (Map.Entry<AnswerTo, Boolean> entry : answers.entrySet()) {
            MapSqlParameterSource mapper = new MapSqlParameterSource()
                    .addValue("date", new Date())
                    .addValue("user_id", userId)
                    .addValue("question_id", entry.getKey().getQuestionId())
                    .addValue("answer", entry.getKey().getAnswer())
                    .addValue("correct", entry.getValue());
            try {
                userAnswerInsert.execute(mapper);
            } catch (Exception e) {
                throw new UniqueIndexException("Вы уже отвечали на вопросы");
            }
        }
    }

    public List<UserAnswer> getAll(Integer userId) {
        String query = "SELECT A.ID, A.QUESTION_ID, Q.QUESTION_ITEM, A.ANSWER, A.CORRECT " +
                "FROM USER_ANSWER A LEFT OUTER JOIN QUESTION Q ON A.QUESTION_ID=Q.ID WHERE USER_ID=?";
        return jdbcTemplate.query(query, rs -> {
            List<UserAnswer> userAnswers = new ArrayList<>();
            while (rs.next()) {
                UserAnswer userAnswer = new UserAnswer();
                userAnswer.setId(rs.getInt("id"));
                Question question = new Question();
                question.setId(rs.getInt("question_id"));
                question.setQuestionItem(rs.getString("question_item"));
                userAnswer.setQuestion(question);
                userAnswer.setUserAnswer(rs.getString("answer"));
                userAnswer.setCorrect(rs.getBoolean("correct"));
                userAnswers.add(userAnswer);
            }
            return userAnswers;
        }, userId);
    }
}