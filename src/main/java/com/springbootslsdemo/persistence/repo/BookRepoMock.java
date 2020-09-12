package com.springbootslsdemo.persistence.repo;

import com.springbootslsdemo.persistence.model.BookNoJpa;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepoMock {

    static final List bookList = new ArrayList();

    static {
        BookNoJpa aBook = new BookNoJpa("some title", "some author");
        bookList.add(aBook);
    }

    public Iterable<BookNoJpa> findAll() {
        return bookList;
    }

    public List<BookNoJpa> findByTitle(String title) {
        return bookList;
    }

    public BookNoJpa findById(long id) {
        return new BookNoJpa("some title", "some author");
    }

    public BookNoJpa save(BookNoJpa input) {
        return input;
    }

    public BookNoJpa findOne(BookNoJpa input) {
        return input;
    }

    public void deleteById(long id) {
        return;
    }
}