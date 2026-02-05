package pl.akademiaqa.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pl.akademiaqa.base.BaseTest;
import pl.akademiaqa.model.User;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@Epic("Users API")
@Feature("User Management")
@Tag("users")
class UserTests extends BaseTest {

    @Test
    @DisplayName("Get all users - should return 10 users")
    @Description("Test sprawdza czy API zwraca listę wszystkich użytkowników")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Get all users")
    void shouldGetAllUsers() {
        Response response = given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("$", hasSize(10))
                .extract()
                .response();

        User[] users = response.as(User[].class);

        assertThat(users)
                .isNotNull()
                .hasSize(10);

        assertThat(users[0].getId()).isNotNull();
        assertThat(users[0].getName()).isNotEmpty();
        assertThat(users[0].getEmail()).contains("@");
    }

    @Test
    @DisplayName("Get user by ID - should return correct user")
    @Description("Test sprawdza czy API zwraca poprawnego użytkownika na podstawie ID")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Get user by ID")
    void shouldGetUserById() {
        int userId = 1;

        Response response = given()
                .pathParam("id", userId)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(userId))
                .body("name", notNullValue())
                .body("email", notNullValue())
                .extract()
                .response();

        User user = response.as(User.class);

        assertThat(user.getId()).isEqualTo(userId);
        assertThat(user.getName()).isNotEmpty();
        assertThat(user.getEmail()).contains("@");
        assertThat(user.getAddress()).isNotNull();
        assertThat(user.getAddress().getCity()).isNotEmpty();
    }

    @Test
    @DisplayName("Get non-existing user - should return 404")
    @Description("Test sprawdza czy API zwraca 404 dla nieistniejącego użytkownika")
    @Severity(SeverityLevel.NORMAL)
    @Story("Get user by ID")
    void shouldReturn404ForNonExistingUser() {
        given()
                .pathParam("id", 999)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Verify user structure")
    @Description("Test sprawdza strukturę obiektu użytkownika")
    @Severity(SeverityLevel.NORMAL)
    @Story("Get user by ID")
    void shouldVerifyUserStructure() {
        User user = given()
                .pathParam("id", 1)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(200)
                .extract()
                .as(User.class);

        assertThat(user)
                .hasNoNullFieldsOrPropertiesExcept("id")
                .hasFieldOrProperty("name")
                .hasFieldOrProperty("username")
                .hasFieldOrProperty("email")
                .hasFieldOrProperty("address")
                .hasFieldOrProperty("phone")
                .hasFieldOrProperty("website")
                .hasFieldOrProperty("company");

        assertThat(user.getAddress())
                .hasFieldOrProperty("street")
                .hasFieldOrProperty("city")
                .hasFieldOrProperty("zipcode");

        assertThat(user.getCompany())
                .hasFieldOrProperty("name")
                .hasFieldOrProperty("catchPhrase");
    }

    @Test
    @DisplayName("Filter users by name")
    @Description("Test sprawdza filtrowanie użytkowników po nazwie")
    @Severity(SeverityLevel.NORMAL)
    @Story("Filter users")
    void shouldFilterUsersByName() {
        String searchName = "Leanne Graham";

        Response response = given()
                .queryParam("name", searchName)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .response();

        User[] users = response.as(User[].class);

        assertThat(users)
                .isNotEmpty()
                .allMatch(user -> user.getName().equals(searchName));
    }
}
