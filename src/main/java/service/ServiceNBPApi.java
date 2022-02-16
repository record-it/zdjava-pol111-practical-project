package service;

import nbpapi.Rate;
import nbpapi.Table;
import repository.RateRepository;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ServiceNBPApi implements ServiceNBP{
    private final RateRepository rates;

    public ServiceNBPApi(RateRepository rates) {
        this.rates = rates;
    }

    @Override
    public double calc(double amount, String source, String target) throws IOException, InterruptedException {
        final List<Rate> rates = this.rates.findByTableAndDate(Table.TABLE_A, null);
        final Optional<Rate> sourceRate = rates.stream()
                .filter(rate -> rate.getCode().equals(source))
                .findFirst();
        final Optional<Rate> targetRate = rates.stream()
                .filter(rate -> rate.getCode().equals(target))
                .findFirst();
        if(sourceRate.isPresent() && targetRate.isPresent()){
            return amount * sourceRate.get().getMid() / targetRate.get().getMid();
        } else {
            throw new InvalidParameterException("Nieznane kody walut!!");
        }
    }

    @Override
    public List<Rate> findAll(Table table, LocalDate date) throws IOException, InterruptedException {
        return rates.findByTableAndDate(table, date);
    }
}