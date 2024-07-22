package com.example.spingsercurewebservicev2;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/public")
public class PublicController {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public PublicController(BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/publishers")
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    @GetMapping("/books/byAuthor")
    public List<Book> getBooksByAuthor(@RequestParam String author) {
        return bookRepository.findByAuthor(author);
    }

    @GetMapping("/publishers/byName")
    public Optional<Publisher> getPublisherByName(@RequestParam String name) {
        return publisherRepository.findByName(name);
    }

    @GetMapping("/books/byPublisher")
    public List<Book> getBooksByPublisher(@RequestParam String publisherName) {
        Optional<Publisher> optionalPublisher = publisherRepository.findByName(publisherName);
        return optionalPublisher.map(bookRepository::findByPublisher).orElse(List.of());
    }
}
