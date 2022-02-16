package repository;
import nbpapi.Rate;
import nbpapi.RateTable;
import nbpapi.Table;
import nbpapi.URIGenerator;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class RateRepositoryNBP implements RateRepository{
    private static final Rate RATE_PLN = Rate.builder().code("PLN").mid(1).currency("złoty").build();
    private ApiRepository<RateTable> rates = new ApiRepository<>(RateTable.class);
    @Override
    public List<Rate> findByTableAndDate(Table table, LocalDate date) throws IOException, InterruptedException {

        final List<Rate> rates = this.rates.getList(URIGenerator.currentTableJson(table)).get(0).getRates();
        rates.add(RATE_PLN);
        return rates;
    }
}
