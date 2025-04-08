package pro.cvartan.test.bookkeeper.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import pro.cvartan.test.bookkeeper.entity.Author;
import pro.cvartan.test.bookkeeper.repository.AuthorRepository;
import pro.cvartan.test.bookkeeper.repository.mapper.AuthorMapper;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository{
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final AuthorMapper authorMapper;

    private final String getQueryBase = """
            SELECT id, "name"
            FROM bk.authors
            """;

    private final String getByIdQuery = getQueryBase + "\nWHERE id = :id";
    private final String getByNameQuery = getQueryBase + "\nWHERE \"name\" LIKE :name";

    private final String insertQuery = """
            INSERT INTO bk.authors
            (id, "name")
            VALUES(nextval('authors_seq'::regclass), :name)
            """;

    private final String updateQuery = """
        UPDATE bk.authors
        SET "name"=:name
        WHERE id = :id
        """;

    private final String deleteQuery = """
            DELETE FROM bk.authors
            WHERE id=:id
            """;

    @Override
    public Optional<Author> getById(Long id) {
        var params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbcTemplate.query(
            getByIdQuery,
            params,
            authorMapper
            ).stream().findFirst();
    }

    @Override
    public List<Author> getByName(String name) {
        var params = new MapSqlParameterSource();
        params.addValue("name", name+"%");
        return jdbcTemplate.query(
            getByNameQuery,
            params,
            authorMapper);
    }
    
    @Override
    public Author add(Author author) throws RuntimeException {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        var params = new MapSqlParameterSource();
        params.addValue("id", author.getId());
        params.addValue("name", author.getName());

        jdbcTemplate.update(
            insertQuery,
            params,
            keyHolder,
            new String[]{"id"}
            );
        
        var id = keyHolder.getKey();
        if(id == null) throw new RuntimeException("Can not return id value in insert statement");

        author.setId(id.longValue());
        return author;
    }

    @Override
    public Author change(Author author) {
        var params = new MapSqlParameterSource();
        params.addValue("id", author.getId());
        params.addValue("name", author.getName());

        jdbcTemplate.update(updateQuery, params);
        return author;        
    }

    @Override
    public Optional<Author> deleteById(Long id) {
        var author = getById(id);

        if (author.isPresent()) {
            var params = new MapSqlParameterSource();
            params.addValue("id", id); 
            jdbcTemplate.update(deleteQuery, params);
        }
        return author;
    }          
}
