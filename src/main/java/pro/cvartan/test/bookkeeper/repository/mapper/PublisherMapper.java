package pro.cvartan.test.bookkeeper.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import pro.cvartan.test.bookkeeper.entity.Publisher;

@Component
public class PublisherMapper implements RowMapper<Publisher>{ 
    public Publisher mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException{
        return new Publisher(
            rs.getLong("id"),
            rs.getString("name")
        );
    }
    
}
