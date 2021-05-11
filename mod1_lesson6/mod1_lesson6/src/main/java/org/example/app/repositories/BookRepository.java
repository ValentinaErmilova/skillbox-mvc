package org.example.app.repositories;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
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
    public List<Book> getByValueFromKey(String key, String value) {
        List<Book> searchedBooks = new ArrayList<>();
        for (Book book : retreiveAll()) {
            switch (key) {
                case "author":
                    if (value.equals(book.getAuthor())) {
                        searchedBooks.add(book);
                    }
                    break;
                case "title":
                    if (value.equals(book.getTitle())) {
                        searchedBooks.add(book);
                    }
                    break;
                case "size":
                    Integer sizeValue = value.isEmpty() ? null : Integer.parseInt(value);
                    if (book.getSize() == null && sizeValue == null || book.getSize() != null && book.getSize().equals(sizeValue)) {
                        searchedBooks.add(book);
                    }
                    break;
            }
        }
        return searchedBooks;
    }

    @Override
    public void removeByValueFromKey(String keyToRemove, String valueToRemove) {
        valueToRemove = valueToRemove.trim();
        for (Book book : retreiveAll()) {
            boolean removeBook = false;
            switch (keyToRemove) {
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
