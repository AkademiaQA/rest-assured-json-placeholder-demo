package pl.akademiaqa.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pl.akademiaqa.base.BaseTest;
import pl.akademiaqa.model.Post;
import pl.akademiaqa.utils.FakerUtils;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@Epic("Posts API")
@Feature("Post Management")
@Tag("posts")
class PostTests extends BaseTest {

        @Test
        @DisplayName("Get all posts - should return 100 posts")
        @Description("Test sprawdza czy API zwraca listę wszystkich postów")
        @Severity(SeverityLevel.CRITICAL)
        @Story("Get all posts")
        void shouldGetAllPosts() {
                Response response = given()
                                .when()
                                .get("/posts")
                                .then()
                                .statusCode(200)
                                .body("$", hasSize(100))
                                .extract()
                                .response();

                Post[] posts = response.as(Post[].class);

                assertThat(posts)
                                .isNotNull()
                                .hasSize(100);
        }

        @Test
        @DisplayName("Get post by ID - should return correct post")
        @Description("Test sprawdza czy API zwraca poprawny post na podstawie ID")
        @Severity(SeverityLevel.CRITICAL)
        @Story("Get post by ID")
        void shouldGetPostById() {
                int postId = 1;

                Response response = given()
                                .pathParam("id", postId)
                                .when()
                                .get("/posts/{id}")
                                .then()
                                .statusCode(200)
                                .body("id", equalTo(postId))
                                .body("title", notNullValue())
                                .body("body", notNullValue())
                                .extract()
                                .response();

                Post post = response.as(Post.class);

                assertThat(post.getId()).isEqualTo(postId);
                assertThat(post.getTitle()).isNotEmpty();
                assertThat(post.getBody()).isNotEmpty();
                assertThat(post.getUserId()).isPositive();
        }

        @Test
        @DisplayName("Create new post - should return 201")
        @Description("Test sprawdza czy można utworzyć nowy post")
        @Severity(SeverityLevel.CRITICAL)
        @Story("Create post")
        void shouldCreateNewPost() {
                Post newPost = Post.builder()
                                .userId(1)
                                .title(FakerUtils.getRandomText(5))
                                .body(FakerUtils.getRandomParagraph())
                                .build();

                Post createdPost = given()
                                .body(newPost)
                                .when()
                                .post("/posts")
                                .then()
                                .statusCode(201)
                                .body("userId", equalTo(newPost.getUserId()))
                                .body("title", equalTo(newPost.getTitle()))
                                .body("body", equalTo(newPost.getBody()))
                                .extract()
                                .as(Post.class);

                assertThat(createdPost.getId()).isNotNull();
                assertThat(createdPost.getUserId()).isEqualTo(newPost.getUserId());
                assertThat(createdPost.getTitle()).isEqualTo(newPost.getTitle());
                assertThat(createdPost.getBody()).isEqualTo(newPost.getBody());
        }

        @Test
        @DisplayName("Update post - should return updated data")
        @Description("Test sprawdza czy można zaktualizować post")
        @Severity(SeverityLevel.NORMAL)
        @Story("Update post")
        void shouldUpdatePost() {
                int postId = 1;
                String updatedTitle = FakerUtils.getRandomText(6);
                String updatedBody = FakerUtils.getRandomParagraph();

                Post updatedPost = Post.builder()
                                .id(postId)
                                .userId(1)
                                .title(updatedTitle)
                                .body(updatedBody)
                                .build();

                Post response = given()
                                .pathParam("id", postId)
                                .body(updatedPost)
                                .when()
                                .put("/posts/{id}")
                                .then()
                                .statusCode(200)
                                .extract()
                                .as(Post.class);

                assertThat(response.getId()).isEqualTo(postId);
                assertThat(response.getTitle()).isEqualTo(updatedTitle);
                assertThat(response.getBody()).isEqualTo(updatedBody);
        }

        @Test
        @DisplayName("Partially update post - should return updated data")
        @Description("Test sprawdza czy można częściowo zaktualizować post używając PATCH")
        @Severity(SeverityLevel.NORMAL)
        @Story("Update post")
        void shouldPartiallyUpdatePost() {
                int postId = 1;
                String updatedTitle = FakerUtils.getRandomText(6);

                String requestBody = String.format("{\"title\": \"%s\"}", updatedTitle);

                Post response = given()
                                .pathParam("id", postId)
                                .body(requestBody)
                                .when()
                                .patch("/posts/{id}")
                                .then()
                                .statusCode(200)
                                .extract()
                                .as(Post.class);

                assertThat(response.getId()).isEqualTo(postId);
                assertThat(response.getTitle()).isEqualTo(updatedTitle);
        }

        @Test
        @DisplayName("Delete post - should return 200")
        @Description("Test sprawdza czy można usunąć post")
        @Severity(SeverityLevel.NORMAL)
        @Story("Delete post")
        void shouldDeletePost() {
                int postId = 1;

                given()
                                .pathParam("id", postId)
                                .when()
                                .delete("/posts/{id}")
                                .then()
                                .statusCode(200);
        }

        @Test
        @DisplayName("Get posts by user ID - should return user's posts")
        @Description("Test sprawdza czy można pobrać posty konkretnego użytkownika")
        @Severity(SeverityLevel.NORMAL)
        @Story("Filter posts")
        void shouldGetPostsByUserId() {
                int userId = 1;

                Response response = given()
                                .queryParam("userId", userId)
                                .when()
                                .get("/posts")
                                .then()
                                .statusCode(200)
                                .body("$", not(empty()))
                                .extract()
                                .response();

                Post[] posts = response.as(Post[].class);

                assertThat(posts)
                                .isNotEmpty()
                                .allMatch(post -> post.getUserId().equals(userId));
        }
}
