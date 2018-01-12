package com.springbazaar.repository;

import com.springbazaar.domain.Person;
import com.springbazaar.domain.Product;
import com.springbazaar.domain.util.SortStringGenerator;
import com.vaadin.data.provider.QuerySortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    private static final String QUERY_FOR_ALL_PRODUCTS_BY_PERSON = "SELECT * " +
            "FROM sb_products " +
            "WHERE person_id = ? ";

    private static final String QUERY_FOR_COUNT_PRODUCTS_BY_PERSON = "SELECT count(*) " +
            "FROM sb_products " +
            "WHERE person_id = ? ";

    private final JdbcTemplate jdbcTemplate;
    private final PersonRepository personRepository;

    @Autowired
    public ProductRepositoryCustomImpl(JdbcTemplate jdbcTemplate, PersonRepository personRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.personRepository = personRepository;
    }

    @Override
    public List<Product> fetchProductsOfPerson(BigInteger personId,
                                               String productCaptionFilter,
                                               int limit,
                                               int offset,
                                               List<QuerySortOrder> sortOrders) {
        StringBuilder sql = new StringBuilder(QUERY_FOR_ALL_PRODUCTS_BY_PERSON);
        List<Object> args = new ArrayList<>();
        args.add(personId);
        setProductCaptionSqlCondition(productCaptionFilter, sql, args);
        args.add(limit);
        args.add(offset);

//        if (sortOrders == null || sortOrders.isEmpty()) {
//            sortOrders = new ArrayList<>();
//            sortOrders.add(new QuerySortOrder("id", SortDirection.ASCENDING));
//        }
        sql.append(SortStringGenerator.generate(sortOrders));
        sql.append(" limit ? offset ?");

        return jdbcTemplate.query(sql.toString(), args.toArray(), (resultSet, i) -> {
            Person personEntity = personRepository.findOne(new BigInteger(resultSet.getString("person_id")));
            Product product = new Product();
            product.setId(new BigInteger(resultSet.getString("id")));
            product.setCaption(resultSet.getString("caption"));
            product.setDescription(resultSet.getString("description"));
            product.setPrice(resultSet.getBigDecimal("price"));
            product.setPerson(personEntity);
            return product;
        });
    }

    @Override
    public int countProducts(BigInteger personId, String productCaptionFilter) {
        StringBuilder sql = new StringBuilder(QUERY_FOR_COUNT_PRODUCTS_BY_PERSON);
        List<Object> args = new ArrayList<>();
        args.add(personId);
        setProductCaptionSqlCondition(productCaptionFilter, sql, args);

        return jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
    }

    private void setProductCaptionSqlCondition(String productCaptionFilter, StringBuilder sql, List<Object> args) {
        if (!productCaptionFilter.isEmpty()) {
            sql.append("AND caption LIKE ? ");
            productCaptionFilter = getPaternProductCaptionFilter(productCaptionFilter);
            args.add(productCaptionFilter);
        }
    }

    private String getPaternProductCaptionFilter(String productCaptionFilter) {
        return "%" + productCaptionFilter.toLowerCase().trim() + "%";
    }
}
