package pro.cvartan.test.bookkeeper.repository;

import java.util.List;
import java.util.Optional;

import pro.cvartan.test.bookkeeper.entity.Publisher;


public interface PublisherRepository {
    Optional<Publisher> getById(Long id);
    List<Publisher> getByName(String name);
    Publisher add(Publisher publisher);
    Publisher change(Publisher publisher);
    Optional<Publisher> deleteById(Long id);    
}
