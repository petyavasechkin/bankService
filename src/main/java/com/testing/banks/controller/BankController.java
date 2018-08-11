package com.testing.banks.controller;


import com.testing.banks.model.Bank;
import com.testing.banks.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class BankController {


    @Autowired
    private BankService bankService;

    @GetMapping("/**")
    public void initBanks() throws IOException {

        List<Bank> banks = bankService.getBanks();
        bankService.storeBanks(banks);
    }

    @GetMapping("/{id}")
    public ResponseEntity getBankById(@PathVariable("id") Long id) {

        Optional<Bank> optionalBank = bankService.getBankById(id);
        if (optionalBank.isPresent())
            return ResponseEntity.ok(optionalBank.get());
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found!");
    }

    @GetMapping("/name/{id}")
    public ResponseEntity getBankNameById(@PathVariable("id") Long id) {

        Optional<Bank> optionalBank = bankService.getBankById(id);
        if (!optionalBank.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found!");
        else
            return ResponseEntity.ok(optionalBank.get().getName());
    }

    @GetMapping("/all")
    public ResponseEntity getBankNameById() {

         List<Bank> banks = (List<Bank>) bankService.getAllBanks();
         return ResponseEntity.ok(banks.toString());
    }
}
