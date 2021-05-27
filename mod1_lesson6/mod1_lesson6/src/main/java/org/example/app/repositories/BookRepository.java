package org.example.app.repositories;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.example.web.dto.forms.BookForm;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements ProjectRepository<Book> {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();

    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(book.hashCode());
        logger.info("store new book: " + book);
        repo.add(book);
    }

    @Override
    public List<Book> getByValueFromKey(Object o) {
        BookForm bookForm = (BookForm) o;
        List<Book> searchedBooks = new ArrayList<>();
        for (Book book : retreiveAll()) {
            switch (bookForm.getKey()) {
                case "author":
                    if (bookForm.getValue().equals(book.getAuthor())) {
                        searchedBooks.add(book);
                    }
                    break;
                case "title":
                    if (bookForm.getValue().equals(book.getTitle())) {
                        searchedBooks.add(book);
                    }
                    break;
                case "size":
                    Integer sizeValue = bookForm.getValue().isEmpty() ? null : Integer.parseInt(bookForm.getValue());
                    if (book.getSize() == null && sizeValue == null || book.getSize() != null && book.getSize().equals(sizeValue)) {
                        searchedBooks.add(book);
                    }
                    break;
            }
        }
        return searchedBooks;
    }

    @Override
    public void remove(Object remove) {
        BookForm bookForm = (BookForm) remove;
        String valueToRemove = bookForm.getValue().trim();
        for (Book book : retreiveAll()) {
            boolean removeBook = false;
            switch (bookForm.getKey()) {
                case "id":
                    Integer idValue = valueToRemove.isEmpty() ? null : Integer.parseInt(valueToRemove);
                    removeBook = book.getId() == null && idValue == null || book.getId() != null && book.getId().equals(idValue);
                    break;
                case "author":
                    removeBook = valueToRemove.equals(book.getAuthor());
                    break;
                case "title":
                    removeBook = valueToRemove.equals(book.getTitle());
                    break;
                case "size":
                    Integer sizeValue = valueToRemove.isEmpty() ? null : Integer.parseInt(valueToRemove);
                    removeBook = book.getSize() == null && sizeValue == null || book.getSize() != null && book.getSize().equals(sizeValue);
                    break;
            }
            if (removeBook) {
                logger.info("remove book completed: " + book);
                repo.remove(book);
            }
        }
    }
}
