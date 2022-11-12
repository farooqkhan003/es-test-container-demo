package com.es.testcontainer.estestcontainer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EsTestContainerApplicationTests extends BaseIT {

    @Test
    void loadContext() {
    }
}
