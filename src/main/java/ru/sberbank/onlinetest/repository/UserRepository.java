package ru.sberbank.onlinetest.repository;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.sberbank.onlinetest.model.Role;
import ru.sberbank.onlinetest.model.User;

import java.util.*;

@Repository
@Transactional(readOnly = true)
public class UserRepository {
    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertUser;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("User")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
    }

    public User get(String username) {
        List<User> users = jdbcTemplate.query("SELECT * FROM User WHERE username=?", ROW_MAPPER, username);
        return setRoles(DataAccessUtils.singleResult(users));
    }


    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        Number newKey = insertUser.executeAndReturnKey(parameterSource);
        user.setId(newKey.intValue());
        insertRoles(user);
        return user;
    }

    //not often operation so consist of 2 queries to database
    public List<User> getAll() {
        Map<Integer, Set<Role>> map = new HashMap<>();
        jdbcTemplate.query("SELECT * FROM Role", rs -> {
            map.computeIfAbsent(rs.getInt("user_id"), userId -> EnumSet.noneOf(Role.class))
                    .add(Role.valueOf(rs.getString("role")));
        });
        List<User> users = jdbcTemplate.query("SELECT * FROM User ORDER BY name, email", ROW_MAPPER);
        users.forEach(u -> u.setRoles(map.get(u.getId())));
        return users;
    }

    private void insertRoles(User u) {
        Set<Role> roles = u.getRoles();
        if (!CollectionUtils.isEmpty(roles)) {
            jdbcTemplate.batchUpdate("INSERT INTO Role (user_id, role) VALUES (?, ?)", roles, roles.size(),
                    (ps, role) -> {
                        ps.setInt(1, u.getId());
                        ps.setString(2, role.name());
                    });
        }
    }

    private User setRoles(User u) {
        if (u != null) {
            List<Role> roles = jdbcTemplate.queryForList("SELECT role FROM Role WHERE user_id=?",
                    Role.class, u.getId());
            u.setRoles(roles);
        }
        return u;
    }
}
