package com.example.spingsercurewebservicev2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public EmployeeController(BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @PostMapping("/addBook")
    public ResponseEntity<?> addBook(@RequestParam String title, @RequestParam String author, @RequestParam Long publisherId) {
        // Find the publisher by ID
        Optional<Publisher> optionalPublisher = publisherRepository.findById(publisherId);

        if (optionalPublisher.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Publisher not found");
        }

        Publisher publisher = optionalPublisher.get();

        // Create and save the book
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublisher(publisher);
        bookRepository.save(book);

        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            bookRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }
    }
}
