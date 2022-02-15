package controller;

import java.util.Scanner;

public class ConsoleController {
    private Scanner scanner;
    private Menu menu;

    public ConsoleController(Scanner scanner, Menu menu) {
        this.scanner = scanner;
        this.menu = menu;
    }

    public void process(){
        while(true) {
            menu.print();
            if (scanner.hasNextInt()){
                final int option = scanner.nextInt();
                scanner.nextLine(); //usunięcie ze skanera entera za liczbą
                if (menu.isValidOption(option)){
                    menu.run(option);
                } else {
                    System.out.println("Brak takiej opcji");
                }
            } else {
                System.out.println("Wpisz numer opcji!");
                scanner.nextLine(); //usnięcie niepoprawnych danych
            }
        }
    }
}
