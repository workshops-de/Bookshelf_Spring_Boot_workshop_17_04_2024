package de.workshops.bookshelf.book;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookRestControllerWithWebEnvironmentIntegrationTest {

  @LocalServerPort
  private int port;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }

  @Test
  void testWithRestAssured() {
    RestAssured.
        given().
          auth().basic("dbUser", "workshops").
          log().all().
        when().
          get("/book").
        then().
          log().all().
        statusCode(200).
          body("author[0]", equalTo("Erich Gamma"));
  }
}
