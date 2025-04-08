package pro.cvartan.test.bookkeeper.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserPrivilegeMapper implements RowMapper<String>{
    public String mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        return new String(rs.getString("privilege_name"));
    }  
}
