package com.es.testcontainer.estestcontainer.controller;


import com.es.testcontainer.estestcontainer.BaseIT;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.TimeUnit;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest extends BaseIT {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(value = 1)
    public void getAllBooks_emptyResults() {
        Flux<BookController.BookDto> flux = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/books")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .returnResult(BookController.BookDto.class)
                .getResponseBody();

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    @Order(value = 2)
    public void createBook_test() throws InterruptedException {
        BookController.BookDto book = createBook("12 rules for life", "Jordan Peterson", 2018, "978-0345816023");

        webTestClient.post()
                .uri("v1/books")
                .body(Mono.just(book), BookController.BookDto.class)
                .exchange()
                .expectStatus().isAccepted()
                .expectBody()
                .jsonPath("$.authorName").isEqualTo("Jordan Peterson")
                .jsonPath("$.title").isEqualTo("12 rules for life")
                .jsonPath("$.isbn").isEqualTo("978-0345816023");

        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    @Order(value = 3)
    public void getAllBooks_countResults() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/books")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookController.BookDto.class)
                .hasSize(1);
    }

    @Test
    @Order(value = 3)
    public void getAllBooks_getResults() {
        Flux<BookController.BookDto> flux = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/books")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .returnResult(BookController.BookDto.class)
                .getResponseBody();

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNextMatches(bookDto -> bookDto.getAuthorName().equals("Jordan Peterson"))
                .verifyComplete();
    }

    @Test
    @Order(value = 3)
    public void getBookByIsbn() {
        Flux<BookController.BookDto> flux = webTestClient.get()
                .uri("/v1/books/{isbn}", "978-0345816023")
                .exchange()
                .expectStatus().isOk()
                .returnResult(BookController.BookDto.class)
                .getResponseBody();

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNextMatches(bookDto -> bookDto.getAuthorName().equals("Jordan Peterson"))
                .verifyComplete();
    }

    //    @Test
//    @Order(value = 3)
//    public void getBookByWrongIsbn_emptyResult() {
//        Flux<BookController.BookDto> flux = webTestClient.get()
//                .uri("/v1/books/{isbn}", "wrong_isbn")
//                .exchange()
//                .expectStatus().isOk()
//                .returnResult(BookController.BookDto.class)
//                .getResponseBody();
//
//        StepVerifier.create(flux)
//                .expectSubscription()
//                .expectNextMatches(bookDto -> bookDto.getAuthorName().equals("Jordan Peterson"))
//                .verifyComplete();
//    }
}
