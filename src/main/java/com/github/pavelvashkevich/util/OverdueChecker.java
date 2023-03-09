package com.github.pavelvashkevich.util;

import com.github.pavelvashkevich.model.Book;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class OverdueChecker {

    private static final int OVERDUE_TIME_IN_DAYS = 10;

    public OverdueChecker() {
    }

    private void checkBookOverdue(Book bookToCheck) {
        long now = Calendar.getInstance().getTimeInMillis();
        if (Objects.isNull(bookToCheck.getBorrowedTime()))
            return;
        long differenceInMilliSec = now - bookToCheck.getBorrowedTime().getTimeInMillis();
        long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMilliSec);
        if (differenceInDays >= OVERDUE_TIME_IN_DAYS)
            bookToCheck.setOverdue(true);

    }
    public void checkBooksOverdue(List<Book> booksToCheck) {
        booksToCheck.forEach(this::checkBookOverdue);
    }
}
