package pro.cvartan.test.bookkeeper.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pro.cvartan.test.bookkeeper.entity.Author;
import pro.cvartan.test.bookkeeper.entity.Publisher;
import pro.cvartan.test.bookkeeper.repository.AuthorRepository;
import pro.cvartan.test.bookkeeper.repository.PublisherRepository;

@Service
@RequiredArgsConstructor
public class DictionaryService {
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    private final Logger logger = LoggerFactory.getLogger(DictionaryService.class);
    
    public Author addAuthor(Author author) throws RuntimeException {
        logger.info("Start adding author");

        if (author.getId() != null) throw new RuntimeException("id must be null");
        return authorRepository.add(author);
    }

    @Secured("ROLE_ADMIN")
    public Author editAuthor(Long id, Author author) {
        logger.info("Start editing author");

        this.getAuthor(id);

        author.setId(id);

        return authorRepository.change(author);
    }

    public Author deleteAuthor(Long id) throws RuntimeException {
        logger.info("Start deleteing author");

        var result = authorRepository.deleteById(id);
        if(result.isEmpty()) throw new RuntimeException("author not found");
        return result.get();
    }

    public Author getAuthor(Long id) throws RuntimeException {
        logger.info("Get author by id");

        var result = authorRepository.getById(id);
        if(result.isEmpty()) throw new RuntimeException("author not found");
        return result.get();
    }

    public List<Author> findAuthorByName(String name) {
        logger.info("Find author by name");

        return authorRepository.getByName(name);
    }

    public Publisher addPublisher(Publisher publisher) throws RuntimeException {
        logger.info("Start adding publisher");

        if(publisher.getId() != null) throw new RuntimeException("id must be null");
        return publisherRepository.add(publisher);
    }

   
    public Publisher editPublisher(Long id, Publisher publisher) {
        logger.info("Start editing publisher");

        return publisherRepository.change(publisher);
    }

    public Publisher deletePublisher(Long id) throws RuntimeException {
        logger.info("Start deleting publisher");

        var result = publisherRepository.deleteById(id);
        if(result.isEmpty()) throw new RuntimeException("publisher not found");
        return result.get();
    }

    public Publisher getPublisher(Long id) throws RuntimeException {
        logger.info("Get publisher by id");

        var result = publisherRepository.getById(id);
        if(result.isEmpty()) throw new RuntimeException("publisher not found");
        return result.get();
    }

    public List<Publisher> findPublisherByName(String name) {
        logger.info("Find publisher by name");

        return publisherRepository.getByName(name);
    }

}
