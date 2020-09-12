package com.springbootslsdemo.web;

import com.springbootslsdemo.persistence.model.BookNoJpa;
import com.springbootslsdemo.persistence.repo.BookRepoMock;
import com.springbootslsdemo.web.exception.BookIdMismatchException;
import com.springbootslsdemo.web.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookControllerMock {

    @Autowired
    private BookRepoMock bookRepository;

    @GetMapping
    public Iterable<BookNoJpa> findAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/title/{bookTitle}")
    public List<BookNoJpa> findByTitle(@PathVariable String bookTitle) {
        return bookRepository.findByTitle(bookTitle);
    }

    @GetMapping("/{id}")
    public BookNoJpa findOne(@PathVariable long id) {
        return bookRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookNoJpa create(@RequestBody BookNoJpa book) {
        BookNoJpa book1 = bookRepository.save(book);
        return book1;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        bookRepository.findById(id);
        bookRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public BookNoJpa updateBook(@RequestBody BookNoJpa book, @PathVariable long id) {
        if (book.getId() != id) {
            throw new BookIdMismatchException();
        }
        bookRepository.findById(id);
        return bookRepository.save(book);
    }
}
