package ru.sberbank.onlinetest.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.sberbank.onlinetest.model.Option;
import ru.sberbank.onlinetest.model.Question;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class QuestionRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertQuestion;

    @Autowired
    public QuestionRepository(JdbcTemplate jdbcTemplate) {
        this.insertQuestion = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Question")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Question> getAll() {
        String query = "SELECT Q.ID AS question_id, Q.DATE AS question_date, Q.QUESTION_ITEM, " +
                "O.ID AS option_id, O.OPTION_ITEM FROM QUESTION Q LEFT OUTER JOIN OPTION O " +
                "ON Q.ID = O.QUESTION_ID ORDER BY Q.ID";
        return new ArrayList<>(Objects.requireNonNull(jdbcTemplate.query(query, new QuestionMapExtractor())));
    }

    public Question save(Question question) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(question);
        Number newKey = insertQuestion.executeAndReturnKey(parameterSource);
        question.setId(newKey.intValue());
        insertOptions(question);
        return question;
    }

    private void insertOptions(Question question) {
        Set<Option> options = question.getOptions();
        if (!CollectionUtils.isEmpty(options)) {
            jdbcTemplate.batchUpdate("INSERT INTO OPTION (question_id, option_item) VALUES (?, ?)", options, options.size(),
                    (ps, option) -> {
                        ps.setInt(1, question.getId());
                        ps.setString(2, option.getOptionItem());
                    });
        }
    }

    private static final class QuestionMapExtractor implements ResultSetExtractor<Collection<Question>> {
        @Override
        public Collection<Question> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Integer, Question> questionById = new HashMap<>();
            while (rs.next()) {
                Integer questionId = rs.getInt("question_id");
                Date questionDate = rs.getDate("question_date");
                String questionItem = rs.getString("question_item");
                Integer optionId = rs.getInt("option_id");
                String optionItem = rs.getString("option_item");
                Question question = questionById.get(questionId);
                if (question == null) {
                    question = new Question(questionId, questionDate, questionItem);
                    questionById.put(question.getId(), question);
                }
                if (optionItem != null) {
                    Option option = new Option();
                    option.setOptionItem(optionItem);
                    option.setId(optionId);
                    question.addOption(option);
                }
            }
            return questionById.values();
        }
    }
}