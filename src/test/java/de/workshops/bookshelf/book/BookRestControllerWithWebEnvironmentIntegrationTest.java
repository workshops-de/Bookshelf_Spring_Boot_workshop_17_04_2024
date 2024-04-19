package de.workshops.bookshelf.book;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BookRestControllerWithWebEnvironmentIntegrationTest {

  @Test
  void testWithRestAssured() {
    RestAssured.
        given().
          log().all().
        when().
          get("/book").
        then().
          log().all().
        statusCode(200).
          body("author[0]", equalTo("Erich Gamma"));
  }
}
