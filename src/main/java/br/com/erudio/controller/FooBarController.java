package br.com.erudio.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "FooBar Endpoint", description = "Operations about FooBar")
@RestController
@RequestMapping("/book-service")
public class FooBarController {

    private final Logger logger = LoggerFactory.getLogger(FooBarController.class);

    @GetMapping("/foo-bar")
    //@Retry(name = "default")
    //@CircuitBreaker(name = "default", fallbackMethod = "fallbackMethod")
    @RateLimiter(name = "default")
    public String fooBar() {
        logger.info("Request to foo-bar is received");
//        ResponseEntity<String> response = new RestTemplate().getForEntity("http://localhost:8080/book-service/foo-bar", String.class);
//
//        return response.getBody();
        return "foo-bar";
    }

    public String fallbackMethod(Exception e) {
        return "fallbackMethod foo-bar";
    }
}
