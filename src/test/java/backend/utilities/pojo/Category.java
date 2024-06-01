package backend.utilities.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Category {
    private int id;
    private String name;
}