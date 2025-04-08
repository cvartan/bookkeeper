package pro.cvartan.test.bookkeeper.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import pro.cvartan.test.bookkeeper.entity.Author;

@Component
public class AuthorMapper implements RowMapper<Author>{
    @Override
    public Author mapRow(@NonNull ResultSet rs ,int rowNum) throws SQLException{
        return new Author(
            rs.getLong("id"),
            rs.getString("name")
        );
    }
}
