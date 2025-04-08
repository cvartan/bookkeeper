package pro.cvartan.test.bookkeeper.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import pro.cvartan.test.bookkeeper.entity.Reader;

@Component
public class ReaderMapper implements RowMapper<Reader> {
    public Reader mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException{
        return new Reader(
            rs.getLong("id"), 
            rs.getString("name"));
    }
    
}
