package com.es.testcontainer.estestcontainer.controller;

import com.es.testcontainer.estestcontainer.model.Book;
import com.es.testcontainer.estestcontainer.service.BookServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/books")
public class BookController {

    private final BookServiceImpl bookService;

    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Flux<Book> getAllBooks() {
        return bookService.getAll();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping
    public Mono<Book> createBook(@RequestBody BookDto book) {
        return bookService.create(BookDto.transform(book));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{isbn}")
    public Mono<Book> getBookByIsbn(@PathVariable String isbn) {
        return bookService.getByIsbn(isbn);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}")
    public Mono<Book> updateBook(@PathVariable String id, @RequestBody BookDto book) {
        return bookService.update(id, BookDto.transform(book));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void deleteBook(@PathVariable String id) {
        bookService.deleteById(id);
    }

    public static class BookDto {

        private String title;

        private Integer publicationYear;

        private String authorName;

        private String isbn;

        static Book transform(BookDto bookDto) {
            Book book = new Book();
            book.setTitle(bookDto.title);
            book.setPublicationYear(bookDto.publicationYear);
            book.setAuthorName(bookDto.authorName);
            book.setIsbn(bookDto.isbn);
            return book;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getPublicationYear() {
            return publicationYear;
        }

        public void setPublicationYear(Integer publicationYear) {
            this.publicationYear = publicationYear;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }
    }
}
