package de.workshops.bookshelf.book;

import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class BookService {

    private final BookRepository bookRepository;

    private List<Book> books;

    @PostConstruct
    void init() {
        books = bookRepository.findAllBooks();
    }

    List<Book> getAllBooks() {
        return books;
    }

    Book searchBookByIsbn(String isbn) throws BookNotFoundException {
        return this.books.stream().filter(book -> hasIsbn(book, isbn)).findFirst().orElseThrow(
            BookNotFoundException::new);
    }

    Book searchBookByAuthor(String author) throws BookNotFoundException {
        return this.books.stream().filter(book -> hasAuthor(book, author)).findFirst().orElseThrow(
            BookNotFoundException::new);
    }

    List<Book> searchBooks(BookSearchRequest request) {
        return this.books.stream()
            .filter(book -> hasAuthor(book, request.author()))
            .filter(book -> hasIsbn(book, request.isbn()))
            .toList();
    }

    Book createBook(Book book) {
        books.add(book);

        return book;
    }

    private boolean hasIsbn(Book book, String isbn) {
        return book.getIsbn().equals(isbn);
    }

    private boolean hasAuthor(Book book, String author) {
        return book.getAuthor().contains(author);
    }
}
