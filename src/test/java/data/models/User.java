package data.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    String name;
    String email;
    String password;
    int age;
}
