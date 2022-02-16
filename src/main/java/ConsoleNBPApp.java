import controller.ConsoleController;
import controller.Menu;
import controller.MenuItem;
import nbpapi.Rate;
import nbpapi.Table;
import repository.RateRepository;
import repository.RateRepositoryNBP;
import repository.RateRepositoryNBPCached;
import service.ServiceNBP;
import service.ServiceNBPApi;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ConsoleNBPApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final RateRepository rates = new RateRepositoryNBPCached();
    private static final ServiceNBP service = new ServiceNBPApi(rates);

    private static void printTable(List<Rate> list, Table table) {
        for (Rate rate : list) {
            switch (table) {
                case TABLE_A:
                case TABLE_B:
                    System.out.println(java.lang.String.format("%-45s %5s %15.4f", rate.getCurrency(), rate.getCode(), rate.getMid()));
                    break;
                case TABLE_C:
                    System.out.println(java.lang.String.format("%-45s %5s %15.4f", rate.getCurrency(), rate.getCode(), rate.getBid()));
            }
        }
    }

    private static void handleOptionTable(Table table) {
        try {
            printTable(service.findAll(table, LocalDate.now()), table);
        } catch (Exception e) {
            System.out.println("Błąd połączenia, nie można odczytać danych z sieci!");
            System.out.println("Bład : " + e.getMessage());
        }
    }

    private static void exchange(){
        //TODO wyświetli listę kodów walut w np. 5 kolumnach
        //USD   EUR   PLN   YYY   UUU
        //BAT   XXX   XXX
        System.out.println("Wpisz kwotę:");
        //TODO oprogramuj sprawdzenie czy poprawna dana liczbowa
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Wpisz kod waluty kwoty:");
        java.lang.String sourceCode = scanner.nextLine();
        System.out.println("Wpisz kod waluty docelowej:");
        java.lang.String targetCode = scanner.nextLine();
        try {
            double result = service.calc(amount, sourceCode, targetCode);
            System.out.println("Kwota po wymianie: " + result);
        } catch (InvalidParameterException e){
            System.out.println("kod waluty");
        } catch (IOException e){
            //TODO komunikaty na wypadek błedu sieci
        } catch (InterruptedException e) {
            //TODO komunikat na wypadek błędu przerwania
        }

    }

    public static void main(java.lang.String[] args) {
        Menu menu = Menu.builder()
                .items(
                        List.of(
                                MenuItem.builder()
                                        .action(() -> handleOptionTable(Table.TABLE_B))
                                        .label("Pobierz tabelę B")
                                        .build(),
                                MenuItem.builder()
                                        .label("Pobierz tabelę C")
                                        .action(() -> handleOptionTable(Table.TABLE_C))
                                        .build(),
                                MenuItem.builder()
                                        .label("Pobierz tabelę A")
                                        .action(() -> handleOptionTable(Table.TABLE_A))
                                        .build(),
                                MenuItem.builder()
                                        .label("Wymień walutę")
                                        .action(() -> exchange())
                                        .build(),
                                MenuItem.builder()
                                        .label("Wyjście")
                                        .action(() -> {
                                            System.exit(0);
                                        })
                                        .build()
                        )
                )
                .build();
        ConsoleController controller = new ConsoleController(scanner, menu);
        controller.process();
    }
}
