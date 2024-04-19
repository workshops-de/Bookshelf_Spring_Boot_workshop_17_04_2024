package de.workshops.bookshelf.book;

import java.net.URI;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class BookExceptionHandler {

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
        .forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    problemDetail.setTitle("Input parameters invalid");
    problemDetail.setType(URI.create("http://localhost:8080/input_parameters_invalid.html"));
    problemDetail.setProperty("errorCategory", "Generic");
    problemDetail.setProperty("timestamp", Instant.now());

    return problemDetail;
  }
}
