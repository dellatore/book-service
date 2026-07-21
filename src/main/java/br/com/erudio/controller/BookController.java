package br.com.erudio.controller;

import br.com.erudio.environment.InstanceInformationService;
import br.com.erudio.model.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/book-service")
public class BookController {

    private final InstanceInformationService instanceInformationService;

    public BookController(InstanceInformationService instanceInformationService) {
        this.instanceInformationService = instanceInformationService;
    }

    @GetMapping("{id}/{currency}")
    public Book findBook(@PathVariable Long id, @PathVariable String currency) {
        return new Book(id,
                "",
                "Java EE 8",
                new Date(),
                29.90,
                currency,
                instanceInformationService.retrievePort());
    }
}
