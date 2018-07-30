package com.testing.banks.repository;

import com.testing.banks.model.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JdbcBankRepository implements CrudRepository<Bank,Long> {

    public static final String INSERT_BANK = "INSERT INTO bank (id, name) VALUES (?,?)";
    public static final String FETCH_BANK = "SELECT * FROM bank where id=?";
    public static final String FETCH_ALL_BANKS = "SELECT * FROM bank";


    RowMapper<Bank> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
        Bank bank = new Bank();
        bank.setId(resultSet.getLong("id"));
        bank.setName(resultSet.getString("name"));
        return bank;
    };

    @Autowired
    JdbcTemplate template;

    @Override
    public <S extends Bank> S save(S s) {

        assert template.update(INSERT_BANK, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, s.getId());
                ps.setString(2, s.getName());
            }
        }) == 1;
        return s;
    }

    @Override
    public <S extends Bank> Iterable<S> saveAll(Iterable<S> iterable) {

        List<Bank> banks = (List<Bank>) iterable;
        template.batchUpdate(INSERT_BANK, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, banks.get(i).getId());
                ps.setString(2, banks.get(i).getName());
            }

            @Override
            public int getBatchSize() {
                return banks.size();
            }
        });

        return (Iterable<S>) banks;
    }

    @Override
    public Optional<Bank> findById(Long id) {

        Bank bank = template.queryForObject(FETCH_BANK, new Object[]{id}, ROW_MAPPER);
        return Optional.of(bank);
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Bank> findAll() {
        return template.query(FETCH_ALL_BANKS, ROW_MAPPER);
    }

    @Override
    public Iterable<Bank> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Bank bank) {

    }

    @Override
    public void deleteAll(Iterable<? extends Bank> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
