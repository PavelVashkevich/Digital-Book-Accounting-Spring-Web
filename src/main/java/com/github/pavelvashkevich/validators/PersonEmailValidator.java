package com.github.pavelvashkevich.validators;

import com.github.pavelvashkevich.model.Person;
import com.github.pavelvashkevich.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class PersonEmailValidator implements Validator {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonEmailValidator(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Optional<Person> personFromDb = peopleRepository.findByEmail(person.getEmail());
        if(personFromDb.isPresent())
            if (person.getId() != personFromDb.get().getId())
                errors.rejectValue("email", "", String.format("The email %s is already taken", person.getEmail()));

    }
}
