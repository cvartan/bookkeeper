package pro.cvartan.test.bookkeeper.entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {
    private Long id;
    private UUID code;
    private Reader reader;
    private Book book;
    @JsonProperty("is_completed")
    private Boolean isCompleted;  
}
