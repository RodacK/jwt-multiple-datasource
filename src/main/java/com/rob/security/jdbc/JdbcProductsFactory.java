package com.rob.security.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class JdbcProductsFactory {

    @Autowired
    @Qualifier("testJdbc")
    private JdbcProducts testJdbc;
    @Autowired
    @Qualifier("alfaJdbc")
    private JdbcProducts alfaJdbc;

    public JdbcProducts getConfig(String user) {
        if (user.equals("alfa")) {
            return alfaJdbc;
        } else {
            return  testJdbc;
        }
    }
}
