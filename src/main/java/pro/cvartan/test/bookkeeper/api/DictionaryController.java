package pro.cvartan.test.bookkeeper.api;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pro.cvartan.test.bookkeeper.entity.Author;
import pro.cvartan.test.bookkeeper.service.DictionaryService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequiredArgsConstructor
public class DictionaryController {
    private final DictionaryService service;

    @PostMapping("/author")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Author> postAuthor(@RequestBody Author author) {
        var result = service.addAuthor(author);
        if(result != null) {
            return new ResponseEntity<Author>(author, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/author") 
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    public ResponseEntity<List<Author>> getAuthorsByName(@RequestParam String name) {
        var result = service.findAuthorByName(name);
        
        return new ResponseEntity<List<Author>>(result, HttpStatus.OK);
    }

    @PutMapping("/author/{id}")
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Author> putAuthor(@PathVariable Long id, @RequestBody Author author) {
        try { 
            var result = service.editAuthor(id, author);
            return new ResponseEntity<Author>(result,HttpStatus.OK);
        }
        catch(Exception e) {
            if(e instanceof AuthorizationDeniedException) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else {
                return new ResponseEntity<Author>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

     }
   

}
