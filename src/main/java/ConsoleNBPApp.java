import controller.ConsoleController;
import controller.Menu;
import controller.MenuItem;

import java.util.List;
import java.util.Scanner;

public class ConsoleNBPApp {
    private static final Scanner scanner= new Scanner(System.in);

    public static void main(String[] args) {
        Menu menu = Menu.builder()
                .items(
                        List.of(
                                MenuItem.builder()
                                        .action(() -> {})
                                        .label("Pobierz tabelę B")
                                        .build(),
                                MenuItem.builder()
                                        .label("Pobierz tabelę C")
                                        .action(() -> {})
                                        .build(),
                                MenuItem.builder()
                                        .label("Pobierz tabelę A")
                                        .action(() -> {
                                        })
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
