package com.neighborhoodlibrary;

import java.time.LocalDate;

public class Book {
    private int id;
    private String isbn;
    private String title;
    private boolean isCheckedOut;
    private String checkedOutTo;
    private LocalDate dueDate;


    public Book(int id, String isbn, String title) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.isCheckedOut = false;
        this.checkedOutTo = "";
        this.dueDate = null;
    }


    public int getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public boolean getIsCheckedOut() {
        return isCheckedOut;
    }

    public String getCheckedOutTo() {
        return checkedOutTo;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getDueDateFormatted() {
        if (dueDate == null) {
            return "Not checked out";
        }
        return dueDate.toString(); // Simple date format
    }


    public boolean checkOut(String name) {
        if (isCheckedOut) {
            System.out.println("Sorry, this book is already checked out to " + checkedOutTo);
            return false;
        } else {
            this.isCheckedOut = true;
            this.checkedOutTo = name;
            // Set due date to 14 days from today
            this.dueDate = LocalDate.now().plusDays(14);
            System.out.println(title + " has been checked out to " + name);
            System.out.println("Due date: " + getDueDateFormatted());
            return true;
        }
    }

    public void checkIn() {
        this.isCheckedOut = false;
        this.checkedOutTo = "";
        this.dueDate = null;
        System.out.println(title + " has been checked in successfully.");
    }
}
