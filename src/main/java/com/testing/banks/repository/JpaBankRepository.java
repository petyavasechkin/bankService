package com.testing.banks.repository;

import com.testing.banks.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBankRepository extends JpaRepository<Bank, Long>{
}
