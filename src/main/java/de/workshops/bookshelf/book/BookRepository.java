package de.workshops.bookshelf.book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class BookRepository {

    private final ObjectMapper objectMapper;

    private final ResourceLoader resourceLoader;

    private List<Book> books;

    @PostConstruct
    void initBookList() throws IOException {
        final var resource = resourceLoader.getResource("classpath:books.json");
        books = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {
        });
    }

    List<Book> findAllBooks() {
        return books;
    }
}
