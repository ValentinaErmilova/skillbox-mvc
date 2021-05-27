package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.forms.BookForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Controller
@RequestMapping(value = "/books")
public class BookShelfController {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookFormRemove", new BookForm());
        model.addAttribute("bookFormSearch", new BookForm());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @GetMapping("/search")
    public String search(@ModelAttribute("bookFormSearch") @Valid BookForm bookFormSearch,
                         BindingResult bindingResult,
                         Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("bookFormRemove", new BookForm());
        model.addAttribute("bookFormSearch", bookFormSearch);
        if (bindingResult.hasErrors()) {
            model.addAttribute("bookList", bookService.getAllBooks());
        } else {
            model.addAttribute("bookList", bookService.search(bookFormSearch));
        }
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("bookFormRemove", new BookForm());
            model.addAttribute("bookFormSearch", new BookForm());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.saveBook(book);
            logger.info("current repository size: " + bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/remove")
    public String removeBook(@ModelAttribute("bookFormRemove") @Valid BookForm bookFormRemove,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookFormSearch", new BookForm());
            model.addAttribute("bookFormRemove", bookFormRemove);
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.remove(bookFormRemove);
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                            Model model) throws Exception {
        try {
            String name = file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            File dir = new File("external_uploads");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
        } catch (FileNotFoundException e) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookFormSearch", new BookForm());
            model.addAttribute("bookFormRemove", new BookForm());
            model.addAttribute("fileNotFound", true);
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        }
        return "redirect:/books/shelf";
    }
}
