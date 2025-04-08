package pro.cvartan.test.bookkeeper.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import pro.cvartan.test.bookkeeper.entity.Publisher;
import pro.cvartan.test.bookkeeper.repository.PublisherRepository;
import pro.cvartan.test.bookkeeper.repository.mapper.PublisherMapper;

@Repository
@RequiredArgsConstructor
public class PublisherRepositoryImpl implements PublisherRepository{
    
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final PublisherMapper publisherMapper;

    private final String getQueryBase = """
        SELECT id, "name"
        FROM bk.publishers
        """;

    private final String getByIdQuery = getQueryBase + "\nWHERE id = :id";
    private final String getByNameQuery = getQueryBase + "\nWHERE \"name\" like :name";

    private final String insertQuery = """
            INSERT INTO bk.publishers
            (id, "name")
            VALUES(nextval('publishers_seq'::regclass), :name)
            """;

    private final String updateQuery = """
        UPDATE bk.publishers
        SET "name"=:name
        WHERE id = :id
        """;

    private final String deleteQuery = """
            DELETE FROM bk.publishers
            WHERE id=:id
            """;

    @Override
    public Optional<Publisher> getById(Long id) {
       var params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbcTemplate.query(
            getByIdQuery,
            params,
            publisherMapper
            ).stream().findFirst();
    }

    @Override
    public List<Publisher> getByName(String name) {
        var params = new MapSqlParameterSource();
        params.addValue("name", name);
        return jdbcTemplate.query(
            getByNameQuery,
            params,
            publisherMapper);        
    }

    @Override
    public Publisher add(Publisher publisher) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        var params = new MapSqlParameterSource();
        params.addValue("id", publisher.getId());
        params.addValue("name", publisher.getName());

        jdbcTemplate.update(
            insertQuery,
            params,
            keyHolder,
            new String[]{"id"}
            );
        
        var id = keyHolder.getKey();
        if(id == null) throw new RuntimeException("Can not return id value in insert statement");

        publisher.setId(id.longValue());
        return publisher;
    }

    @Override
    public Publisher change(Publisher publisher) {
        var params = new MapSqlParameterSource();
        params.addValue("id", publisher.getId());
        params.addValue("name", publisher.getName());

        jdbcTemplate.update(updateQuery, params);
        return publisher;   
    }

    @Override
    public Optional<Publisher> deleteById(Long id) {
        var publisher = getById(id);

        if (publisher.isPresent()) {
            var params = new MapSqlParameterSource();
            params.addValue("id", id); 
            jdbcTemplate.update(deleteQuery, params);
        }
        return publisher;
    }    

}
