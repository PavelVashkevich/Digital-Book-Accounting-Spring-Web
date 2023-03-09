package com.github.pavelvashkevich.controllers;

import com.github.pavelvashkevich.model.Person;
import com.github.pavelvashkevich.services.PeopleService;
import com.github.pavelvashkevich.validators.PersonDateOfBirthValidator;
import com.github.pavelvashkevich.validators.PersonEmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final PersonEmailValidator personEmailValidator;
    private final PersonDateOfBirthValidator personDateOfBirthValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, PersonEmailValidator personValidator, PersonDateOfBirthValidator personDateOfBirthValidator) {
        this.peopleService = peopleService;
        this.personEmailValidator = personValidator;
        this.personDateOfBirthValidator = personDateOfBirthValidator;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping
    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personEmailValidator.validate(person, bindingResult);
        personDateOfBirthValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findOne(id));
        model.addAttribute("books", peopleService.findPersonBooks(id));
        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                               @PathVariable("id") int id) {
        personEmailValidator.validate(person, bindingResult);
        personDateOfBirthValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }

        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }
}