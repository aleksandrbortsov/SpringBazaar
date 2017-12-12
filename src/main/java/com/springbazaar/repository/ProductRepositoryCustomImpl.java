package com.springbazaar.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepositoryCustomImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


//    @Override
//    public void updateUserProducts(User user) {
//        jdbcTemplate.update("UPDATE Product p SET p.price ='5' WHERE p.user = ?", user);
//    }
}
