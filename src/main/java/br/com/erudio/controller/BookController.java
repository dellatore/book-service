package br.com.erudio.controller;

import br.com.erudio.environment.InstanceInformationService;
import br.com.erudio.model.Book;
import br.com.erudio.repository.BookRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        book.setCurrency(currency);
        book.setEnvironment(instanceInformationService.retrievePort());

        return book;
    }
}
