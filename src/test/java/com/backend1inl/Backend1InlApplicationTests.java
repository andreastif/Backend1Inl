package com.backend1inl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;



// Quick Fix, kan gitignore och hämta pass från en prop file med @Value ist
@SpringBootTest
@ActiveProfiles("test")
class Backend1InlApplicationTests {

    @Test
    void contextLoads() {
    }

}
