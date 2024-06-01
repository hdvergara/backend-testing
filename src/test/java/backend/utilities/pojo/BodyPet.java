package backend.utilities.pojo;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class BodyPet {
    public int id;
    public Category category;
    public String name;
    public ArrayList<Tag> tags;
    public String status;
}