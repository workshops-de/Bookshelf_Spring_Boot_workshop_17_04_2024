package de.workshops.bookshelf.book;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class BookRestControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private BookRestController bookRestController;

  @Test
  void getAllBooks() throws Exception {
    // arrange

    // act
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/book"));

    // assert
    MvcResult mvcResult = resultActions.andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].title", is("Clean Code")))
        .andReturn();

    String jsonPayload = mvcResult.getResponse().getContentAsString();
    List<Book> books = objectMapper.readValue(jsonPayload, new TypeReference<>() {
    });

    assertEquals(3, books.size());
    assertEquals("Clean Code", books.get(1).getTitle());
  }

  @Test
  void testWithRestAssuredMockMvc() {
    RestAssuredMockMvc.standaloneSetup(bookRestController);
    RestAssuredMockMvc.
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
