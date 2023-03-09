package com.github.pavelvashkevich.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    @Size(min = 2, max = 100, message = "Length of book name should be between 2 and 100 characters.")
    @Column(name = "name")
    private String name;
    @NotEmpty
    @Size(min = 2, max = 100, message = "Length of author's name should be between 2 and 100 characters.")
    @Column(name = "author")
    private String author;

    @Column(name = "year_of_publish")
    @Positive(message = "Year of public should be positive number.")
    @Past(message = "Year of publish can not be set to the future")
    private int yearOfPublish;

    @Column(name = "borrowed_time")
    @Temporal(value = TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Calendar borrowedTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person patron;

    @Transient
    private boolean isOverdue;


    public Book(String name, String author, int yearOfPublish) {
        this.name = name;
        this.author = author;
        this.yearOfPublish = yearOfPublish;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfPublish() {
        return yearOfPublish;
    }

    public void setYearOfPublish(int yearOfPublish) {
        this.yearOfPublish = yearOfPublish;
    }

    public Person getPatron() {
        return patron;
    }

    public void setPatron(Person patron) {
        this.patron = patron;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && yearOfPublish == book.yearOfPublish && Objects.equals(name, book.name) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, author, yearOfPublish);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", yearOfPublish=" + yearOfPublish +
                '}';
    }

    public Calendar getBorrowedTime() {
        return borrowedTime;
    }

    public void setBorrowedTime(Calendar borrowedTime) {
        this.borrowedTime = borrowedTime;
    }

    public boolean isOverdue() {
        return isOverdue;
    }

    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
    }
}
