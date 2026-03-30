package com.petstore.automation.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class BodyPet {
    private int id;
    private Category category;
    private String name;
    private ArrayList<Tag> tags;
    private String status;
}
