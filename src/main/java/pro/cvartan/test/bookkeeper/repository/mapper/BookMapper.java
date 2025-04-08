package pro.cvartan.test.bookkeeper.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import pro.cvartan.test.bookkeeper.entity.Author;
import pro.cvartan.test.bookkeeper.entity.Book;
import pro.cvartan.test.bookkeeper.entity.Publisher;

@Component
public class BookMapper implements RowMapper<Book>{
    @Override
    public Book mapRow(@NonNull ResultSet rs,int rowNum) throws SQLException {
        var author = new Author(
            rs.getLong("author_id"),
            rs.getString("author_name")
        );

        var publisher = new Publisher(
            rs.getLong("publisher_id"),
            rs.getString("publisher_name")
        );

        return new Book(
            rs.getLong("id"),
            rs.getString("isbn"),
            rs.getString("title"),
            author,
            rs.getString("title_rus"),
            publisher,
            rs.getDate("publish_date")
        );
    }   
}
