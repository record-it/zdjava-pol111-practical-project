import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nbpapi.Rate;
import nbpapi.Table;
import repository.RateRepository;
import repository.RateRepositoryNBPCached;
import service.ServiceNBP;
import service.ServiceNBPApi;

import java.io.IOException;
import java.time.LocalDate;

public class FxNBPApp extends Application {
    private VBox root = new VBox();
    private Scene scene = new Scene(root, 600,400);
    private Label amountLabel = new Label("Kwota do wymiany");
    private TextField amount = new TextField();
    private ComboBox<Rate> sourceCode = new ComboBox<>();
    private ComboBox<Rate> targetCode = new ComboBox<>();
    private Label result = new Label("0,0");
    private Button runExchange = new Button("Przelicz");

    private RateRepository repository = new RateRepositoryNBPCached();
    private ServiceNBP serviceNBP = new ServiceNBPApi(repository);

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        builGUI(stage);
    }

    private void calcExchangeResult(){
        double input = Double.parseDouble(amount.getText());
        try {
            double output = serviceNBP.calc(input,
                    sourceCode.getSelectionModel().getSelectedItem().getCode(),
                    targetCode.getSelectionModel().getSelectedItem().getCode()

            );
            result.setText(String.format("%6.2f", output));
        } catch (IOException e) {
            result.setText("Błąd pobierania danych!");
        } catch (InterruptedException e) {
            result.setText("Przerwano połączenie z serwerem!");
        }
    }

    private void builGUI(Stage stage){
        runExchange.setOnAction(event -> {
            calcExchangeResult();
        });
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(amountLabel,
                amount,
                sourceCode,
                targetCode,
                result,
                runExchange
        );
        root.setAlignment(Pos.TOP_CENTER);
        try {
            sourceCode.getItems().addAll(serviceNBP.findAll(Table.TABLE_A, LocalDate.now()));
            targetCode.getItems().addAll(serviceNBP.findAll(Table.TABLE_A, LocalDate.now()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
        stage.setTitle("Waluty z NBP");
        stage.show();
    }
}
