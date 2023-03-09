package com.github.pavelvashkevich.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Name should not be empty.")
    @Size(min = 2, max = 100, message = "Length of full name should be between 2 and 100 characters.")
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Date of birth cannot be set to the future")
    private Date dateOfBirth;

    @Column(name = "email")
    @Email(message = "Not valid email format")
    @NotBlank(message = "Email must not be blank")
    private String email;

    @Column(name = "contact_phone")
    @Pattern(regexp = "\\+\\d{12}", message = "Phone number should be in '+XXXXXXXXXXXX' format")
    private String contactPhone;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patron")
    private List<Book> books;

    public Person(String fullName, Date dateOfBirth, String email, String contactPhone) {
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.contactPhone = contactPhone;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }


    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(fullName, person.fullName) && Objects.equals(dateOfBirth, person.dateOfBirth) && Objects.equals(email, person.email) && Objects.equals(contactPhone, person.contactPhone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, dateOfBirth, email, contactPhone);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", email='" + email + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                '}';
    }
}
