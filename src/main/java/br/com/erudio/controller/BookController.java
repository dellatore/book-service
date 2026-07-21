package br.com.erudio.controller;

import br.com.erudio.environment.InstanceInformationService;
import br.com.erudio.model.Book;
import br.com.erudio.model.dto.Exchange;
import br.com.erudio.repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
@RequestMapping("/book-service")
public class BookController {

    private final InstanceInformationService instanceInformationService;
    private final BookRepository repository;

    public BookController(InstanceInformationService instanceInformationService, BookRepository repository) {
        this.instanceInformationService = instanceInformationService;
        this.repository = repository;
    }

    @GetMapping("{id}/{currency}")
    public Book findBook(@PathVariable Long id, @PathVariable String currency) {
        Book book = repository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        HashMap<String, String> params = new HashMap<>();

        params.put("from", "USD");
        params.put("to", currency);
        params.put("amount", book.getPrice().toString());

        ResponseEntity<Exchange> response = new RestTemplate().getForEntity(
                "http://localhost:8000/exchange-service/{amount}/{from}/{to}",
                Exchange.class,
                params
        );

        Exchange exchange = response.getBody();


        book.setCurrency(currency);
        book.setEnvironment(instanceInformationService.retrievePort());
        book.setPrice(exchange.getConvertedValue() != null ? exchange.getConvertedValue() : book.getPrice());

        return book;
    }
}
