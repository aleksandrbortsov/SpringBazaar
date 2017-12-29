package com.springbazaar.repository;

import com.springbazaar.domain.Person;
import com.springbazaar.domain.Product;
import com.springbazaar.domain.util.SortStringGenerator;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    private final JdbcTemplate jdbcTemplate;
    private final PersonRepository personRepository;

    @Autowired
    public ProductRepositoryCustomImpl(JdbcTemplate jdbcTemplate, PersonRepository personRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.personRepository = personRepository;
    }

    @Override
    public List<Product> fetchProductsOfPerson(String personIdFilter,
                                               int limit,
                                               int offset,
                                               List<QuerySortOrder> sortOrders) {
        if (personIdFilter == null) {
            //TODO remove 2
            personIdFilter = "2";
        }
//        personIdFilter = "%" + personIdFilter.toLowerCase().trim() + "%";

//        if (sortOrders == null || sortOrders.isEmpty()) {
//            sortOrders = new ArrayList<>();
//            sortOrders.add(new QuerySortOrder("id", SortDirection.ASCENDING));
//        }

        String sql = "SELECT * " +
                "FROM sb_products " +
                "WHERE person_id = ? " +
                SortStringGenerator.generate(sortOrders) + " limit ? offset ?";

        return jdbcTemplate.query(sql, new Object[]{personIdFilter, limit, offset}, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet resultSet, int i) throws SQLException {
                Person personEntity = personRepository.findOne(new BigInteger(resultSet.getString("person_id")));
                Product product = new Product();
                product.setId(new BigInteger(resultSet.getString("id")));
                product.setCaption(resultSet.getString("caption"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setPerson(personEntity);
                return product;
            }
        });
    }

    @Override
    public int countProducts(String personIdFilter) {
        if (personIdFilter == null) {
            //TODO remove 2
            personIdFilter = "2";
        }
//        personIdFilter = "%" + filter.toLowerCase().trim() + "%";

        String sql = "SELECT count(*) " +
                "FROM sb_products " +
                "WHERE person_id = ? ";

        return jdbcTemplate.queryForObject(sql, new Object[]{personIdFilter}, Integer.class);
    }
}
