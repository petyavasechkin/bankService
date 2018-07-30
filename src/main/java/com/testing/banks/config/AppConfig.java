package com.testing.banks.config;

import com.testing.banks.repository.JdbcBankRepository;
import com.testing.banks.repository.JpaBankRepository;
import com.testing.banks.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Autowired
    JpaBankRepository jpaBankRepository;

    @Primary
    @Bean
    public CrudRepository getJdbcRepository(){
        return new JdbcBankRepository();
    }

    @Bean(name="bank_service")
    public BankService getBankService(){

        BankService bankService = new BankService(getJdbcRepository());
        return bankService;
    }

}
