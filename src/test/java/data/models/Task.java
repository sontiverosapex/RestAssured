package data.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Task {
    String description;
}
