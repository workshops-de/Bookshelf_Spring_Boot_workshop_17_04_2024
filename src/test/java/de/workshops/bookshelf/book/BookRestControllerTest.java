package de.workshops.bookshelf.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookRestControllerTest {

  @Autowired
  private BookRestController bookRestController;

  @Test
  void getAllBooks() {
    assertNotNull(bookRestController.getAllBooks());
    assertEquals(3, bookRestController.getAllBooks().size());
  }
}
