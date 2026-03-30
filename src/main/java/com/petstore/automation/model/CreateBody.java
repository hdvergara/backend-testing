package com.petstore.automation.model;

import java.util.ArrayList;

public final class CreateBody {

    private CreateBody() {
    }

    public static BodyPet body(int id, String name, int idCategory, String nameCategory,
                               int idTag, String nameTag, String status) {
        Tag tag = Tag.builder()
                .id(idTag)
                .name(nameTag)
                .build();

        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(tag);

        Category category = Category.builder()
                .id(idCategory)
                .name(nameCategory)
                .build();

        return BodyPet.builder()
                .id(id)
                .category(category)
                .name(name)
                .tags(tags)
                .status(status)
                .build();
    }
}
