package com.pecodigos.literalura.ui;

import com.pecodigos.literalura.services.BookService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class UI {

    private final Scanner sc = new Scanner(System.in);
    private final BookService bookService;

    public UI(BookService bookService) {
        this.bookService = bookService;
    }

    public void showMenu() {
        int chosenOption = -1;

        while (chosenOption != 0) {
            System.out.print
                    ("""
                    
                    1- Get book information by title.
                    2- List current saved books.
                    3- List current saved authors.
                    4- List saved books by language.
                    5- List living authors in a given year.
                    0- Exit.
                    
                    """);

            System.out.print("Enter your option: ");
            chosenOption = sc.nextInt();

            System.out.println();
            sc.nextLine();

            switch (chosenOption) {
                case 0:
                    System.out.println("Goodbye!");
                    break;
                case 1:
                    printBookInformationByTitle(this.sc);
                    break;
                case 2:
                    printBooksList();
                    break;
                case 3:
                    printAuthorsList();
                    break;
                case 4:
                    printBookInformationByLanguage(this.sc);
                    break;
                case 5:
                    printLivingAuthorsList(this.sc);
                    break;
                default:
                    System.out.println("Not a valid option.");
                    break;
            }
        }
    }

    private void printBookInformationByTitle(Scanner sc) {
        System.out.print("Enter the book's title: ");
        var bookTitle = sc.nextLine();
        System.out.println(bookService.getBookInfo(bookTitle));
    }

    private void printBookInformationByLanguage(Scanner sc) {
        System.out.print("Enter the books language: ");
        var language = sc.nextLine();
        printList(bookService.listBooksByLanguage(language));
    }

    private void printBooksList() {
        System.out.println("Books' list:");
        printList(bookService.listBooks());
    }

    private void printLivingAuthorsList(Scanner sc) {
        System.out.print("Enter living author's year: ");
        var year = sc.nextInt();
        sc.nextLine();
        printList(bookService.listLivingAuthors(year));
    }

    private void printAuthorsList() {
        System.out.println("Authors' list:");
        printList(bookService.listAuthors());
    }

    private <T> void printList(List<T> list) {
        System.out.println();
        var counter = new AtomicInteger(1);
        list.forEach(item -> System.out.printf("%d-%n%s%n", counter.getAndIncrement(), item));
    }
}
