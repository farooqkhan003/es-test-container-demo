package com.es.testcontainer.estestcontainer.service;

import com.es.testcontainer.estestcontainer.model.Book;
import org.elasticsearch.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {
    Mono<Book> getByIsbn(String isbn);

    Flux<Book> getAll();

    Flux<Book> findByAuthor(String authorName);

    Mono<Book> create(Book book) throws RuntimeException;

    void deleteById(String id);

    Mono<Book> update(String id, Book book) throws ResourceNotFoundException;
}
