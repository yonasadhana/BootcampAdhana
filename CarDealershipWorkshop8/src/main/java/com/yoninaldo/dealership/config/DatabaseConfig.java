package com.yoninaldo.dealership.config;

import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;

public class DatabaseConfig {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/car_dealership";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "136307";

    private static BasicDataSource dataSource;

    static {
        dataSource = new BasicDataSource();
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxOpenPreparedStatements(100);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}