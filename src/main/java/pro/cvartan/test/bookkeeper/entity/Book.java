package pro.cvartan.test.bookkeeper.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {
    @Id
    private Long id;
    private String isbn;
    private String title;
    private Author author;  
    private String titleRus;
    private Publisher publisher;
    @JsonProperty("publish_date")
    private Date publishDate;
}
