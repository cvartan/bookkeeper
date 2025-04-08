package pro.cvartan.test.bookkeeper.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPrivilege {
    private Long id;
    private User user;
    @JsonProperty("privilege_name")
    private String privilegeName;
}
