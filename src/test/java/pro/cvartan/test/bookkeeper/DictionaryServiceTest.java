package pro.cvartan.test.bookkeeper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pro.cvartan.test.bookkeeper.entity.Author;
import pro.cvartan.test.bookkeeper.entity.Publisher;
import pro.cvartan.test.bookkeeper.service.DictionaryService;

@SpringBootTest
public class DictionaryServiceTest {
    @Test
    void testAuthorsLivecicle(@Autowired DictionaryService service) {        
        var result = service.addAuthor(new Author(null, "Unit Test Mock"));
        assertEquals(result, service.getAuthor(result.getId()));
        var list = service.findAuthorByName("Unit");
        assertEquals(result, list.getFirst());
        assertEquals(result, service.deleteAuthor(result.getId()));
        assertThrows(RuntimeException.class, () -> {service.getAuthor(result.getId());});
    }

    @Test 
    void testPublishersLivecicle(@Autowired DictionaryService service) {
        var result = service.addPublisher(new Publisher(null, "Unit Test Mock"));
        assertEquals(result, service.getPublisher(result.getId()));
        assertEquals(result, service.deletePublisher(result.getId()));
        assertThrows(RuntimeException.class, () -> {service.getPublisher(result.getId());});
    }
}
