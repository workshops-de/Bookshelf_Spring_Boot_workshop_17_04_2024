package de.workshops.bookshelf.book;

import static org.hamcrest.Matchers.hasSize;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(
    controllers = BookRestController.class,
    includeFilters = @ComponentScan.Filter(
        type = FilterType.ANNOTATION,
        classes = ConfigurationProperties.class
    )
)
@AutoConfigureMockMvc
class BookRestControllerMockitoIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BookService bookService;

  @Test
  @WithMockUser
  void getAllBooks() throws Exception {
    List<Book> mockedBookList = new ArrayList<>();
    mockedBookList.add(new Book());
    Mockito.when(bookService.getAllBooks()).thenReturn(mockedBookList);

    mockMvc.perform(MockMvcRequestBuilders.get("/book"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
  }
}
