package com.github.pavelvashkevich.controllers;

import com.github.pavelvashkevich.model.Book;
import com.github.pavelvashkevich.model.Person;
import com.github.pavelvashkevich.services.BooksService;
import com.github.pavelvashkevich.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(@RequestParam(name="page", required = false) Integer page,
                        @RequestParam(name="books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(name="sort_by_year", required = false) Boolean isSortByYear,
                        Model model) {
        if (!Objects.isNull(page) && !Objects.isNull(booksPerPage) && !Objects.isNull(isSortByYear)) {
            model.addAttribute("books", booksService.findAllSortByYearOfPublish(page, booksPerPage));
            return "books/index";
        }
        if (!Objects.isNull(page) && !Objects.isNull(booksPerPage)) {
            model.addAttribute("books", booksService.findAll(page, booksPerPage));
            return "books/index";
        }
        if (!Objects.isNull(isSortByYear)) {
            model.addAttribute("books", booksService.findAllSortByYearOfPublish());
            return "books/index";
        }
        model.addAttribute("books", booksService.findAll());
        return "books/index";
    }

    @GetMapping("/new")
    public String createBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @GetMapping("/search")
    public String searchBook(@RequestParam(required = false, name = "pattern") String pattern, Model model) {
        if (!Objects.isNull(pattern))
            model.addAttribute("books", booksService.findByNameStartingWith(pattern));
        return "books/search";
    }


    @PostMapping
    public String saveBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, @ModelAttribute("person") Person person, Model model) {
        model.addAttribute("book", booksService.findOne(id));
        model.addAttribute("people", peopleService.findAll());
        model.addAttribute("bookPersonInfo", booksService.findPatronInfo(id));
        return "books/show";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id) {
        booksService.releaseBook(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assignPerson(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        booksService.assignPatron(id, person);
        return "redirect:/books";
    }
}
