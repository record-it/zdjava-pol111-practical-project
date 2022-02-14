import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nbpapi.Rate;
import nbpapi.RateTable;
import nbpapi.Table;
import nbpapi.URIGenerator;

import javax.lang.model.type.ReferenceType;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URIGenerator.currentTableJson(Table.TABLE_A))
                .GET()
                .build();
        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        final List<RateTable> rateTableList = mapper.readValue(response.body(), new TypeReference<List<RateTable>>() {
        });
        RateTable rateTable = rateTableList.get(0);
        System.out.println("Tabela: \t\t" + rateTable.getTable());
        System.out.println("Numer: \t\t\t" + rateTable.getNo());
        System.out.println("Data ważności: \t" + rateTable.getEffectiveDate());
        System.out.println("=========================================================");
        for (Rate rate: rateTable.getRates()) {
            System.out.println(String.format("%-35s %5s %15.4f", rate.getCurrency(), rate.getCode(), rate.getMid()));
        }

    }
}
