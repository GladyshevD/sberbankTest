package ru.sberbank.onlinetest.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.onlinetest.model.CorrectAnswer;
import ru.sberbank.onlinetest.model.Question;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class CorrectAnswerRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CorrectAnswerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CorrectAnswer> getAll() {
        String query = "SELECT C.QUESTION_ID, C.ANSWER FROM CORRECT_ANSWER C";

        return jdbcTemplate.query(query, rs -> {
            List<CorrectAnswer> correctAnswers = new ArrayList<>();
            while (rs.next()) {
                CorrectAnswer correctAnswer = new CorrectAnswer();
                Question question = new Question();
                question.setId(rs.getInt("question_id"));
                correctAnswer.setQuestion(question);
                correctAnswer.setAnswer(rs.getString("answer"));
                correctAnswers.add(correctAnswer);
            }
            return correctAnswers;
        });
    }
}
