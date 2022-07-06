package com.autorush.rushchat.config;

import static org.assertj.core.api.Assertions.assertThat;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class DatabaseConfigurationTest {
    @Autowired
    HikariDataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Value("${spring.datasource.hikari.minimum-idle}")
    int minimumIdle;

    @Test
    @DisplayName("HikariCP 설정 값 확인")
    public void testHikariConnectionPoolValue() throws SQLException{
        try (Connection connection = dataSource.getConnection()){
            /*  GIVEN   */
            final DatabaseMetaData metaData = connection.getMetaData();
            /*  WHEN   */
            /*  THEN   */
            assertThat(metaData.getDatabaseProductName()).isEqualTo("MariaDB");
            assertThat(metaData.getDriverName()).isEqualTo("MariaDB Connector/J");
            assertThat(dataSource.getMinimumIdle()).isEqualTo(minimumIdle);
        }
    }


}