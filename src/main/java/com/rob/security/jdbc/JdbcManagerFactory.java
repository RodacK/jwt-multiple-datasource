package com.rob.security.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

@Component
public class JdbcManagerFactory {

    @Autowired
    private UserDetailsManager userDetailsManager;

    public enum ACCOUNTS{ALFA, TEST};

    @Autowired
    @Qualifier("testJdbc")
    private JdbcProducts testJdbc;
    @Autowired
    @Qualifier("alfaJdbc")
    private JdbcProducts alfaJdbc;

    public JdbcProducts getProductConfig(ACCOUNTS account) {
        switch (account) {
            case ALFA:
                return alfaJdbc;
            default:
                return testJdbc;
        }
    }

    public UserDetailsManager getUserManager(ACCOUNTS account) {
        switch (account) {
            case ALFA:
                return userDetailsManager;
            default:
                return userDetailsManager;
        }
    }
}
