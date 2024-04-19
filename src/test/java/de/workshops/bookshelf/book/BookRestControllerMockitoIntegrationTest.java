package de.workshops.bookshelf.book;

import static org.hamcrest.Matchers.hasSize;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class BookRestControllerMockitoIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BookService bookService;

  @Test
  void getAllBooks() throws Exception {
    List<Book> mockedBookList = new ArrayList<>();
    mockedBookList.add(
        new Book(
            "Clean Code",
            "Das einzige praxisnahe Buch, mit dem Sie lernen, guten Code zu schreiben!",
            "Robert C. Martin",
            "978-3826655487")
    );
    Mockito.when(bookService.getAllBooks()).thenReturn(mockedBookList);

    mockMvc.perform(MockMvcRequestBuilders.get("/book"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
  }
}
