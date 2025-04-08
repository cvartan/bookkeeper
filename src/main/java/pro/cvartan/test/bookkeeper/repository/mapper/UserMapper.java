package pro.cvartan.test.bookkeeper.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import pro.cvartan.test.bookkeeper.entity.User;

@Component
public class UserMapper implements RowMapper<User>{ 
    public User mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException{
        return new User(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("login"),
            rs.getString("pass_hash"),
            rs.getBoolean("is_blocked"),
            null
            );
    }    
}
