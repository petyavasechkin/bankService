package com.testing.banks;

import com.testing.banks.model.Bank;
import com.testing.banks.service.BankService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BanksApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BanksApplicationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BanksApplicationTest.class);

    @Autowired
    BankService bankService;


    Object object = new Object();

    @Test
    public void testBankService() throws IOException, InterruptedException {

        LOGGER.error(bankService.getBankFilename());
        List<Bank> banks = bankService.getBanks();
        bankService.storeBanks(banks);

        Optional<Bank> bankOptional = bankService.getBankById(10040000L);
        if (bankOptional.isPresent()) {
            LOGGER.error(bankOptional.get().getName());
            System.out.println(bankOptional.get().getName());
        }

        List<Bank> bankList = (List<Bank>) bankService.getAllBanks();
        LOGGER.error(bankList.toString());

    }

}