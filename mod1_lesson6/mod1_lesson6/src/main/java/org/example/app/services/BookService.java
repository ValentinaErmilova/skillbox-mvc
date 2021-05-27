package org.example.app.services;

import org.example.app.repositories.ProjectRepository;
import org.example.web.dto.Book;
import org.example.web.dto.forms.BookForm;
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

    public void remove(BookForm removeBook) {
        bookRepo.remove(removeBook);
    }

    public List<Book> search(BookForm searchBook) {
        return bookRepo.getByValueFromKey(searchBook);
    }
}
