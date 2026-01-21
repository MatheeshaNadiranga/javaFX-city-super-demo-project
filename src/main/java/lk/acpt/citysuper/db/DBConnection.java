package lk.acpt.citysuper.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;
    private HikariDataSource dataSource;


    public DBConnection() throws SQLException, ClassNotFoundException {

        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/city_super");
        config.setUsername("root");
        config.setPassword("12345");

        config.setMaximumPoolSize(5);
        config.setMinimumIdle(1);
        config.setMaxLifetime(30000);

        dataSource = new HikariDataSource(config);
    }

    public static DBConnection getDbConnection() throws SQLException, ClassNotFoundException {

        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
