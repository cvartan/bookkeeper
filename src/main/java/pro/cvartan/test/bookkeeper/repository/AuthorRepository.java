package pro.cvartan.test.bookkeeper.repository;

import java.util.List;
import java.util.Optional;

import pro.cvartan.test.bookkeeper.entity.Author;

public interface AuthorRepository {
    Optional<Author> getById(Long id);
    List<Author> getByName(String name);
    Author add(Author author);
    Author change(Author author);
    Optional<Author> deleteById(Long id);    
}
