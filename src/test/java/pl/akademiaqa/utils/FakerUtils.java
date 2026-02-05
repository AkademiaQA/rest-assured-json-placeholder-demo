package pl.akademiaqa.utils;

import com.github.javafaker.Faker;

import java.util.Locale;

public class FakerUtils {

    private static final Faker faker = new Faker(Locale.ENGLISH);

    private FakerUtils() {
    }

    public static String getRandomName() {
        return faker.name().fullName();
    }

    public static String getRandomUsername() {
        return faker.name().username();
    }

    public static String getRandomEmail() {
        return faker.internet().emailAddress();
    }

    public static String getRandomPhoneNumber() {
        return faker.phoneNumber().phoneNumber();
    }

    public static String getRandomWebsite() {
        return faker.internet().domainName();
    }

    public static String getRandomStreet() {
        return faker.address().streetAddress();
    }

    public static String getRandomCity() {
        return faker.address().city();
    }

    public static String getRandomZipCode() {
        return faker.address().zipCode();
    }

    public static String getRandomCompanyName() {
        return faker.company().name();
    }

    public static String getRandomCatchPhrase() {
        return faker.company().catchPhrase();
    }

    public static String getRandomText(int words) {
        return faker.lorem().sentence(words);
    }

    public static String getRandomParagraph() {
        return faker.lorem().paragraph();
    }

    public static int getRandomNumber(int min, int max) {
        return faker.number().numberBetween(min, max);
    }

    public static Faker getFaker() {
        return faker;
    }
}
