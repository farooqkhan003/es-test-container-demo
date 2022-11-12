package com.es.testcontainer.estestcontainer;


import com.es.testcontainer.estestcontainer.controller.BookController;
import com.es.testcontainer.estestcontainer.model.Book;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ExtendWith(SpringExtension.class)
public abstract class BaseIT {
    @Container
    protected static ElasticsearchContainer elasticsearchContainer = new ElasticTestContainer();

    @Autowired
    private ElasticsearchRestTemplate template;

    @BeforeAll
    static void setUp() {
        elasticsearchContainer.start();
    }

    @AfterAll
    static void destroy() {
        // runs after every class
        elasticsearchContainer.stop();
    }

    protected BookController.BookDto createBook(String title, String authorName, int publicationYear, String isbn) {
        BookController.BookDto book = new BookController.BookDto();
        book.setTitle(title);
        book.setAuthorName(authorName);
        book.setPublicationYear(publicationYear);
        book.setIsbn(isbn);

        return book;
    }
}
