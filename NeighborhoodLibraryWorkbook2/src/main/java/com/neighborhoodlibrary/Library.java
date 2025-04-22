package com.neighborhoodlibrary;

import java.util.Scanner;

public class Library {
    private Book[] inventory = new Book[20];
    private String[] userNames = new String[100];
    private int[] userBookCounts = new int[100];
    private int userCount = 0;
    private Scanner scanner = new Scanner(System.in);


    public Library() {
        initializeInventory();
    }

    private void initializeInventory() {
        inventory[0]  = new Book(1, "978-0061120084", "To Kill a Mockingbird");
        inventory[1]  = new Book(2, "978-0451524935", "1984");
        inventory[2]  = new Book(3, "978-0743273565", "The Great Gatsby");
        inventory[3]  = new Book(4, "978-0316769488", "The Catcher in the Rye");
        inventory[4]  = new Book(5, "978-0141439518", "Pride and Prejudice");
        inventory[5]  = new Book(6, "978-0679783268", "Crime and Punishment");
        inventory[6]  = new Book(7, "978-0486280615", "The Adventures of Huckleberry Finn");
        inventory[7]  = new Book(8, "978-0547928227", "The Hobbit");
        inventory[8]  = new Book(9, "978-0679732761", "Brave New World");
        inventory[9]  = new Book(10,"978-0140283334", "Lord of the Flies");
        inventory[10] = new Book(11,"978-0142437247", "Don Quixote");
        inventory[11] = new Book(12,"978-0553213119", "Moby Dick");
        inventory[12] = new Book(13,"978-0374528379", "The Trial");
        inventory[13] = new Book(14,"978-0486282114", "Frankenstein");
        inventory[14] = new Book(15,"978-0141439693", "Wuthering Heights");
        inventory[15] = new Book(16,"978-0141439563", "Great Expectations");
        inventory[16] = new Book(17,"978-0143105947", "War and Peace");
        inventory[17] = new Book(18,"978-0140449266", "Anna Karenina");
        inventory[18] = new Book(19,"978-0142437209", "Jane Eyre");
        inventory[19] = new Book(20,"978-0684801223", "The Old Man and the Sea");
    }


    private int simpleStringToInt(String str) {

        if (str == null || str.length() == 0) {
            return -1;
        }


        if (str.length() == 1) {
            char c = str.charAt(0);
            if (c >= '0' && c <= '9') {
                return c - '0';
            } else {
                return -1; // Not a digit
            }
        }

        int number = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);


            if (c >= '0' && c <= '9') {
                number = number * 10;

                number = number + (c - '0');
            } else {
                return -1;
            }
        }

        return number;
    }

    public void showHomeScreen() {
        boolean exitProgram = false;

        while (exitProgram == false) {
            System.out.println("\n");
            System.out.println("*******************************************");
            System.out.println("*                                         *");
            System.out.println("*       NEIGHBORHOOD LIBRARY SYSTEM       *");
            System.out.println("*                                         *");
            System.out.println("*******************************************");
            System.out.println("*                                         *");
            System.out.println("*  1. Show Available Books                *");
            System.out.println("*  2. Show Checked Out Books              *");
            System.out.println("*  3. Exit                                *");
            System.out.println("*                                         *");
            System.out.println("*******************************************");
            System.out.print("\nEnter your choice (1-3): ");

            String input = scanner.nextLine().trim();
            int choice = simpleStringToInt(input);

            if (choice == -1) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            if (choice == 1) {
                showAvailableBooks();
            } else if (choice == 2) {
                showCheckedOutBooks();
            } else if (choice == 3) {
                exitProgram = true;
                System.out.println("\n*******************************************");
                System.out.println("*                                         *");
                System.out.println("*  Thank you for using our library!       *");
                System.out.println("*              Goodbye!                   *");
                System.out.println("*                                         *");
                System.out.println("*******************************************");
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showAvailableBooks() {
        System.out.println("\n");
        System.out.println("*******************************************");
        System.out.println("*            AVAILABLE BOOKS              *");
        System.out.println("*******************************************");
        boolean hasAvailableBooks = false;

        for (int i = 0; i < inventory.length; i++) {
            Book book = inventory[i];
            if (book.getIsCheckedOut() == false) {
                System.out.println("ID: " + book.getId() + " | ISBN: " + book.getIsbn() + " | Title: " + book.getTitle());
                hasAvailableBooks = true;
            }
        }

        if (hasAvailableBooks == false) {
            System.out.println("No books are currently available.");
            return;
        }

        System.out.println("\nEnter a book ID to check out, or X to return to the home screen:");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("X")) {
            return;
        }

        int bookId = simpleStringToInt(input);

        if (bookId == -1) {
            System.out.println("Please enter a valid book ID.");
            return;
        }

        Book selectedBook = findBookById(bookId);

        if (selectedBook == null) {
            System.out.println("Book with ID " + bookId + " not found.");
            return;
        }

        if (selectedBook.getIsCheckedOut()) {
            System.out.println("This book is already checked out.");
            return;
        }

        System.out.print("Enter your name: ");
        String name = scanner.nextLine().trim();

        int currentCheckouts = getUserCheckoutCount(name);
        int MAX_BOOKS_PER_USER = 3;
        if (currentCheckouts >= MAX_BOOKS_PER_USER) {
            System.out.println("Sorry, you have already checked out the maximum of " + MAX_BOOKS_PER_USER + " books.");
            return;
        }

        if (selectedBook.checkOut(name)) {
            updateUserCheckoutCount(name, currentCheckouts + 1);
        }
    }

    private void showCheckedOutBooks() {
        System.out.println("\n");
        System.out.println("*******************************************");
        System.out.println("*           CHECKED OUT BOOKS             *");
        System.out.println("*******************************************");
        boolean hasCheckedOutBooks = false;

        for (Book book : inventory) {
            if (book.getIsCheckedOut()) {
                System.out.println("ID: " + book.getId() + " | ISBN: " + book.getIsbn() + " | Title: " + book.getTitle() +
                        " | Checked Out To: " + book.getCheckedOutTo() + " | Due Date: " + book.getDueDateFormatted());
                hasCheckedOutBooks = true;
            }
        }

        if (hasCheckedOutBooks == false) {
            System.out.println("No books are currently checked out.");
            System.out.println("Returning to main menu...");
            return;
        }

        System.out.println("\nEnter C to check in a book, or X to return to the home screen:");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("X")) {
            return;
        } else if (input.equalsIgnoreCase("C")) {
            checkInBook();
        } else {
            System.out.println("Invalid option. Returning to home screen.");
        }
    }

    private void checkInBook() {
        System.out.print("Enter the ID of the book you want to check in: ");
        String input = scanner.nextLine().trim();
        int bookId = simpleStringToInt(input);

        if (bookId == -1) {
            System.out.println("Please enter a valid book ID.");
            return;
        }

        Book book = findBookById(bookId);

        if (book == null) {
            System.out.println("Book with ID " + bookId + " not found.");
            return;
        }

        if (book.getIsCheckedOut() == false) {
            System.out.println("This book is not checked out.");
            return;
        }

        String userName = book.getCheckedOutTo();
        int currentCount = getUserCheckoutCount(userName);
        if (currentCount > 0) {
            updateUserCheckoutCount(userName, currentCount - 1);
        }

        book.checkIn();
        System.out.println("Book returned successfully!");
    }

    private Book findBookById(int id) {
        for (Book book : inventory) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    private int getUserCheckoutCount(String name) {
        for (int i = 0; i < userCount; i++) {
            if (userNames[i].equals(name)) {
                return userBookCounts[i];
            }
        }
        return 0; // User not found
    }

    private void updateUserCheckoutCount(String name, int count) {
        // Check if user exists
        for (int i = 0; i < userCount; i++) {
            if (userNames[i].equals(name)) {
                userBookCounts[i] = count;
                return;
            }
        }

        userNames[userCount] = name;
        userBookCounts[userCount] = count;
        userCount++;
    }
}