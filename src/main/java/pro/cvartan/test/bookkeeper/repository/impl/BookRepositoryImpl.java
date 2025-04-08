package pro.cvartan.test.bookkeeper.repository.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import pro.cvartan.test.bookkeeper.entity.Book;
import pro.cvartan.test.bookkeeper.repository.BookRepository;
import pro.cvartan.test.bookkeeper.repository.mapper.BookMapper;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository{

    private final String getAllQuery = """
            SELECT b.id, b.isbn, b.title, a.id author_id, a.name author_name, b.title_rus, p.id publisher_id, p.name publisher_name, b.publish_date 
            FROM bk.books b
            JOIN bk.authors a ON a.id = b.author_id
            JOIN bk.publishers p ON p.id = b.publisher_id
            """;

    private final String getByIdQuery = getAllQuery+"\nWHERE b.id = :id";

    private final String insertQuery = """
            INSERT INTO bk.books
            (id, isbn, title, title_rus, publish_date, author_id, publisher_id)
            VALUES(nextval('books_seq'::regclass),:isbn, :title, :title_rus, :publish_date, :author_id, :publisher_id)
            """;

    private final String updateQuery = """
            UPDATE bk.books
            SET isbn=:isbn, title=:title, title_rus=:title_rus, publish_date=:publish_date, author_id=:author_id, publisher_id=:publisher_id
            WHERE id=:id
            """;
    private final String deleteQuery = """
            DELETE FROM bk.books
            WHERE id=:id
            """;

    private String BookFilter (
        String isbn,
        String title,
        String titleRus,
        String authorName,
        String publisherName,
        Date publishDateStart,
        Date publishDateEnd
    ) {
            var where = new StringBuilder("\nWHERE 1=1");
            if(!isbn.isEmpty()) where.append("\nAND b.isbn=:isbn");
            if(!title.isEmpty()) where.append("\nAND b.title LIKE :title");
            if (!titleRus.isEmpty()) where.append("\nAND b.title_rus LIKE :title_rus");
            if (!authorName.isEmpty()) where.append("\nAND a.name LIKE :author_name");
            if (!publisherName.isEmpty()) where.append("\n AND p.name LIKE :publisher_name");
            if (publishDateStart != null) where.append("\n AND b.publish_date >= :publish_date_start");
            if (publishDateEnd != null) where.append("\n AND b.publish_date <= :publish_date_end");
            return where.toString();
    }

    private final BookMapper bookMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<Book> getAll() {
        return jdbcTemplate.query(getAllQuery, bookMapper);
    }

    public Optional<Book> getById(Long id) {
        var params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbcTemplate.query(
                getByIdQuery,
                params,
                bookMapper
            ).stream().findFirst();
    }

    public List<Book> getByFilter(
        String isbn,
        String title,
        String titleRus,
        String authorName,
        String pulisherName,
        Date publishDateStart,
        Date publishDateEnd
        ) {
        var queryString = getAllQuery+BookFilter(isbn, title, titleRus, authorName, pulisherName, publishDateStart, publishDateEnd);

        var params = new MapSqlParameterSource();
        params.addValue("isbn", isbn);
        params.addValue("title", title+"%");
        params.addValue("title_rus", titleRus+"%");
        params.addValue("author_name", authorName+"%");
        params.addValue("publisher_name", pulisherName+"%");
        params.addValue("publish_date_start", publishDateStart);
        params.addValue("publish_date_end", publishDateEnd);
       
        return jdbcTemplate.query(
            queryString,
            params,
            bookMapper
        );
    }

    @Override
    public Book save(Book book) {
        var params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("isbn", book.getIsbn());
        params.addValue("title", book.getTitle());
        params.addValue("title_rus", book.getTitleRus());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("publisher_id", book.getPublisher().getId());
        params.addValue("publish_date", book.getPublishDate());


        if (book.getId() == null) {
            var id = addBook(insertQuery,params);
            book.setId(id);
            return book;
        }

        jdbcTemplate.update(updateQuery, params);
        
        return book;
    }

    @SuppressWarnings("null")
    private Long addBook(String queryString, MapSqlParameterSource params) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
            queryString,
            params,
            keyHolder,
            new String[]{"id"}
        );

        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<Book> deleteById(Long id) {
        var book = this.getById(id);
        if(book.isPresent()) {
            var params = new MapSqlParameterSource();
            params.addValue("id", id);      
            jdbcTemplate.update(deleteQuery, params);
        }

        return book;
    }
    
}
