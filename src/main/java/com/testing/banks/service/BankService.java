package com.testing.banks.service;

import com.testing.banks.model.Bank;
import com.testing.banks.repository.JpaBankRepository;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
@ConfigurationProperties(prefix="app")
public class BankService {

    private String bankFilename;
    private CrudRepository repository;

    public BankService(CrudRepository repository) {
        this.repository = repository;
    }

    public List<Bank> getBanks() throws IOException {

        List<Bank> banks = new ArrayList<>();
        Scanner scanner = new Scanner(new ClassPathResource(bankFilename).getFile());

        scanner.next();
        while(scanner.hasNext()){

            String[]bankDetails = scanner.next().split(";");
            Bank bank =  new Bank();
            bank.setId(Long.parseLong(bankDetails[1]));
            bank.setName(bankDetails[0]);
            banks.add(bank);
        }

        return banks;
    }

    public void storeBanks(List<Bank> banks){

        repository.saveAll(banks);

    }

    public Optional<Bank> getBankById(Long id){

        Optional<Bank> bank = repository.findById(id);
        return bank;
    }

    public Iterable<Bank> getAllBanks() {

        Iterable<Bank> banks = repository.findAll();
        return banks;
    }


    public String getBankFilename() {
        return bankFilename;
    }
    public void setBankFilename(String bankFilename) {
        this.bankFilename = bankFilename;
    }
}
