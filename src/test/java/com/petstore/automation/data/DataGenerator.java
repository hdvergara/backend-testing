package com.petstore.automation.data;

import net.datafaker.Faker;

public final class DataGenerator {

    private DataGenerator() {
    }

    public static int randomIdPet() {
        return new Faker().number().numberBetween(1, 99);
    }

    public static String randomNamePet() {
        return new Faker().funnyName().name();
    }
}
