package com.example.spingsercurewebservicev2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public AdminController(BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    // Thêm một sách mới
    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestParam String title, @RequestParam String author, @RequestParam Long publisherId) {
        Optional<Publisher> optionalPublisher = publisherRepository.findById(publisherId);
        if (optionalPublisher.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Publisher publisher = optionalPublisher.get();
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublisher(publisher);
        bookRepository.save(book);

        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    // Sửa một sách
    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestParam String title, @RequestParam String author, @RequestParam Long publisherId) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Optional<Publisher> optionalPublisher = publisherRepository.findById(publisherId);
        if (optionalPublisher.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Book book = optionalBook.get();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublisher(optionalPublisher.get());
        bookRepository.save(book);

        return ResponseEntity.ok(book);
    }

    // Xóa một sách
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (!bookRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Thêm một nhà xuất bản mới
    @PostMapping("/publishers")
    public ResponseEntity<Publisher> addPublisher(@RequestParam String name) {
        Publisher publisher = new Publisher();
        publisher.setName(name);
        publisherRepository.save(publisher);

        return ResponseEntity.status(HttpStatus.CREATED).body(publisher);
    }

    // Sửa một nhà xuất bản
    @PutMapping("/publishers/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable Long id, @RequestParam String name) {
        Optional<Publisher> optionalPublisher = publisherRepository.findById(id);
        if (optionalPublisher.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Publisher publisher = optionalPublisher.get();
        publisher.setName(name);
        publisherRepository.save(publisher);

        return ResponseEntity.ok(publisher);
    }

    // Xóa một nhà xuất bản
    @DeleteMapping("/publishers/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
        if (!publisherRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        publisherRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
