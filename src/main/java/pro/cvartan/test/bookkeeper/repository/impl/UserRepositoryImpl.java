package pro.cvartan.test.bookkeeper.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import pro.cvartan.test.bookkeeper.entity.User;
import pro.cvartan.test.bookkeeper.repository.UserRepository;
import pro.cvartan.test.bookkeeper.repository.mapper.UserMapper;
import pro.cvartan.test.bookkeeper.repository.mapper.UserPrivilegeMapper;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;
    private final UserPrivilegeMapper userPrivilegeMapper;

    private Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    private final String getUserQueryBase = """
            SELECT id, "name", login, pass_hash, is_blocked
            FROM bk.users
            """;

    private final String getUserByLoginQuery = getUserQueryBase + "WHERE login = :login";


    private final String getUserPrivilegiesListQuery = """
            SELECT privilege_name
            FROM bk.user_privilegies
            WHERE user_id = :user_id
            """;

    public User getUserByLogin(String login) throws RuntimeException {
        try {
            var params = new MapSqlParameterSource();
            params.addValue("login", login);

            logger.info(getUserByLoginQuery);

            var users = jdbcTemplate.query(
                getUserByLoginQuery, 
                params,
                userMapper);

            logger.info("user",users);

            if(users.isEmpty()) throw new RuntimeException("user is not found");

            var user = users.getFirst();
        
            params.addValue("user_id", user.getId());

            var privilegies = jdbcTemplate.query(
                getUserPrivilegiesListQuery,
                params,
                userPrivilegeMapper);

            user.setPrivilegeNames(privilegies);

            return user;
        }
        catch (Exception e) {
            logger.error("error", e);
            throw new RuntimeException(e);
        }
    }

}
