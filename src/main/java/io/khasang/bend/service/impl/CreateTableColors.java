package io.khasang.bend.service.impl;

import io.khasang.bend.service.CreateTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CreateTableColors implements CreateTable {
    private JdbcTemplate jdbcTemplate;

    @Override
    public String getTableCreationStatus(String val) {

        try {
            jdbcTemplate.execute("DROP TABLE IF EXISTS " + val);
            jdbcTemplate.execute("CREATE TABLE public.colors(id integer NOT NULL, name varchar(255), PRIMARY KEY (id))");
            return "table " + val + " created";
        } catch (BadSqlGrammarException e) {
            return "table creation failed: " + e.getMessage();
        }
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
