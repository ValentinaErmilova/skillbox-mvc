package org.example.app.services;

import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final ProjectRepository<Book> bookRepo;

    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retreiveAll();
    }

    public void saveBook(Book book) {
        bookRepo.store(book);
    }

    public void removeBookByValueFromKey(String bookKeyToRemove, String bookIdToRemove) {
        bookRepo.removeByValueFromKey(bookKeyToRemove, bookIdToRemove);
    }

    public List<Book> searchBookByValueFromKey(String bookKeyToSearch, String bookValueToSearch) {
        return bookRepo.getByValueFromKey(bookKeyToSearch, bookValueToSearch);
    }
}
