package de.workshops.bookshelf.book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.net.URI;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
@Validated
class BookRestController {

    private final ObjectMapper mapper;

    private final ResourceLoader resourceLoader;

    private List<Book> books;

    @PostConstruct
    void init() throws IOException {
        this.books = mapper.readValue(
                resourceLoader
                        .getResource("classpath:books.json")
                        .getInputStream(),
                new TypeReference<>() {}
        );
    }

    @GetMapping
    List<Book> getAllBooks() {
        return books;
    }

    @GetMapping("/{isbn}")
    Book getSingleBook(@PathVariable String isbn) throws BookNotFoundException {
        return this.books.stream().filter(book -> hasIsbn(book, isbn)).findFirst().orElseThrow(
            BookNotFoundException::new);
    }

    @GetMapping(params = "author")
    Book searchBookByAuthor(@RequestParam @Size(min = 3) String author) throws BookNotFoundException {
        return this.books.stream().filter(book -> hasAuthor(book, author)).findFirst().orElseThrow(
            BookNotFoundException::new);
    }

    @PostMapping("/search")
    List<Book> searchBooks(@RequestBody @Valid BookSearchRequest bookSearchRequest) {
        return this.books.stream()
            .filter(book -> hasAuthor(book, bookSearchRequest.author()))
            .filter(book -> hasIsbn(book, bookSearchRequest.isbn()))
            .toList();
    }

    @ExceptionHandler(BookNotFoundException.class)
    ProblemDetail handleBookException(BookNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail
            .forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Book not found");
        problemDetail.setType(URI.create("http://localhost:8080/book_exception.html"));
        problemDetail.setProperty("errorCategory", "Generic");
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail handleBookException(MethodArgumentNotValidException e) {
        ProblemDetail problemDetail = ProblemDetail
            .forStatusAndDetail(HttpStatus.I_AM_A_TEAPOT, e.getMessage());
        problemDetail.setTitle("Input parameters invalid");
        problemDetail.setType(URI.create("http://localhost:8080/input_parameters_invalid.html"));
        problemDetail.setProperty("errorCategory", "Generic");
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    private boolean hasIsbn(Book book, String isbn) {
        return book.getIsbn().equals(isbn);
    }

    private boolean hasAuthor(Book book, String author) {
        return book.getAuthor().contains(author);
    }
}
