package com.es.testcontainer.estestcontainer.service;

import com.es.testcontainer.estestcontainer.model.Book;
import com.es.testcontainer.estestcontainer.repository.BookRepository;
import org.elasticsearch.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Mono<Book> getByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public Flux<Book> getAll() {
        Flux<Book> all = bookRepository.findAll();
        System.out.println("####" + all.count().block());

        return all;
    }

    @Override
    public Flux<Book> findByAuthor(String authorName) {
        return bookRepository.findByAuthorName(authorName);
    }

    @Override
    public Mono<Book> create(Book book) throws RuntimeException {
        return bookRepository.save(book);
    }

    @Override
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Mono<Book> update(String id, Book book) throws ResourceNotFoundException {

        return bookRepository.findById(id)
                .switchIfEmpty(Mono.empty())
                .flatMap(b -> {
                    b.setIsbn(book.getIsbn());
                    b.setAuthorName(book.getAuthorName());
                    b.setPublicationYear(book.getPublicationYear());
                    b.setTitle(book.getTitle());

                    return bookRepository.save(b);
                });

    }
}
