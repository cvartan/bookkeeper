package pro.cvartan.test.bookkeeper.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import pro.cvartan.test.bookkeeper.entity.Book;

public interface BookRepository{
    Optional<Book> getById(Long id);
    List<Book> getAll();
    List<Book> getByFilter(
        String isbn,
        String title,
        String title_rus,
        String authorName,
        String pulisherName,
        Date publishDateStart,
        Date publishDateEnd
    );
    Book save(Book book);
    Optional<Book> deleteById(Long id);
}
