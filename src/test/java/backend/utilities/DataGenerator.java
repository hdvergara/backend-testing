package backend.utilities;

import com.github.javafaker.Faker;

public class DataGenerator {

    /**
     * Method to generate IdPet randomly
     *
     * @return generated IdPet
     */
    public static int randomIdPet(){
        Faker faker = new Faker();
        return faker.number().numberBetween(1,99);
    }

    /**
     * Method to generate Pet's name randomly
     * @return generated name
     */
    public static String randomNamePet(){
        Faker faker = new Faker();
        return faker.funnyName().name();
    }
}
