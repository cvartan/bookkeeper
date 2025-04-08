package pro.cvartan.test.bookkeeper.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private String login;
    @JsonProperty("pass_hash")
    private String passHash;
    @JsonProperty("is_blocked")
    private Boolean isBlocked;
    List<String> privilegeNames;
}
