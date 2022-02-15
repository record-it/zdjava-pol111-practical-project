package repository;

import nbpapi.Rate;
import nbpapi.Table;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface RateRepository {
    List<Rate> findByTableAndDate(Table table, LocalDate date) throws IOException, InterruptedException;
}
