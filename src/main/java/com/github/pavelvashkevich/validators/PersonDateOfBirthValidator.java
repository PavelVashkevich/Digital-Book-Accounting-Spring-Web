package com.github.pavelvashkevich.validators;

import com.github.pavelvashkevich.model.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Calendar;
import java.util.Date;

@Component
public class PersonDateOfBirthValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (person.getDateOfBirth().after(new Date(1920, Calendar.JANUARY, 1))) {
            errors.rejectValue("dateOfBirth", "", "The date of birth must be after January 1st, 1920.");
        }
    }
}
