package com.rob.security.jdbc;

import com.rob.security.model.Product;
import com.rob.security.service.ProductDetailService;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.naming.NameNotFoundException;
import java.util.List;

public class JdbcProducts extends JdbcDaoSupport implements ProductDetailService {

    public static final String DEF_PRODUCTS_BY_NAME_QUERY = "select name,description "
            + "from products "
            + "where name = ?";
    @Override
    public Product loadProductByName(String name) throws NameNotFoundException {
        RowMapper<Product> mapper = (rs, rowNum) -> {
            String name1 = rs.getString(1);
            String description = rs.getString(2);
            return new Product(name1, description);
        };
        // @formatter:on
         List<Product> result = getJdbcTemplate().query(DEF_PRODUCTS_BY_NAME_QUERY, mapper, name);

        if (result.size() == 0) {
            this.logger.debug("Query returned no results for product '" + name + "'");
            throw new NameNotFoundException("no results for product '" + name + "'");
        }
        return  result.get(0);
    }
}
