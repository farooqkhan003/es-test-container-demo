package com.es.testcontainer.estestcontainer.repository;

import com.es.testcontainer.estestcontainer.model.Book;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BookRepository extends ReactiveElasticsearchRepository<Book, String> {

    Flux<Book> findByAuthorName(String authorName);

    Mono<Book> findByIsbn(String isbn);
}