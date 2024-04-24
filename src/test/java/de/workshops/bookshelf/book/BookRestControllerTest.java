package de.workshops.bookshelf.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
class BookRestControllerTest {

  @Autowired
  private BookRestController bookRestController;

  @Test
  @WithMockUser
  void getAllBooks() {
    assertNotNull(bookRestController.getAllBooks());
    assertEquals(3, bookRestController.getAllBooks().size());
  }
}
