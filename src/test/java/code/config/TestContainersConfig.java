package code.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@TestConfiguration
public class TestContainersConfig {

  static final String CONTAINER = "postgres:16.3";

  @Bean
  PostgreSQLContainer<?> postgresqlContainer() {
    return container;
  }

  @ServiceConnection
  static PostgreSQLContainer<?> container = new PostgreSQLContainer<>(CONTAINER);

  @Bean
  DataSource dataSource(final PostgreSQLContainer<?> postgresqlContainer) {
    return DataSourceBuilder.create()
        .type(HikariDataSource.class)
        .driverClassName(postgresqlContainer.getDriverClassName())
        .url(postgresqlContainer.getJdbcUrl())
        .username(postgresqlContainer.getUsername())
        .password(postgresqlContainer.getPassword())
        .build();
  }
}