package code.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Objects;

@Component
@Slf4j
@AllArgsConstructor
public class DatabaseInitializer {

  private Environment environment;

  @Bean
  public DataSource dataSource() {
    String url = initializeIfNotExists();
    final HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(Objects.requireNonNull(environment.getProperty("spring.datasource.driver-class-name")));
    hikariConfig.setJdbcUrl(url);
    hikariConfig.setUsername(environment.getProperty("spring.datasource.username"));
    hikariConfig.setPassword(environment.getProperty("spring.datasource.password"));
    hikariConfig.setSchema(environment.getProperty("spring.datasource.hikari.schema"));
    hikariConfig.setConnectionTestQuery("SELECT 1");
    hikariConfig.setPoolName("springHikariCP");
    hikariConfig.setMaximumPoolSize(20);
    hikariConfig.setConnectionTimeout(20000);
    hikariConfig.setMinimumIdle(10);
    hikariConfig.setIdleTimeout(300000);

    HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
    JdbcTemplate jdbcTemplate = new JdbcTemplate(hikariDataSource);

    String schemaName = environment.getProperty("spring.datasource.hikari.schema");
    String query = "SELECT EXISTS(SELECT 1 FROM information_schema.schemata WHERE schema_name = '%s')".formatted(schemaName);
    if (Boolean.FALSE.equals(jdbcTemplate.queryForObject(query, Boolean.class))) {
      String createSchemaSql = "CREATE SCHEMA app";
      jdbcTemplate.execute(createSchemaSql);
    }
    return hikariDataSource;
  }

  private String initializeIfNotExists() {
    String profile = environment.getActiveProfiles()[0];

    DriverManagerDataSource initialDataSource = new DriverManagerDataSource();
    initialDataSource.setDriverClassName("org.postgresql.Driver");
    initialDataSource.setUrl(environment.getProperty("spring.datasource.url"));
    initialDataSource.setUsername(environment.getProperty("spring.datasource.username"));
    initialDataSource.setPassword(environment.getProperty("spring.datasource.password"));
    JdbcTemplate jdbcTemplate = new JdbcTemplate(initialDataSource);

    String suffix = profile.equals("prod") ? "" : "_dev";
    String dbName = "template" + suffix;

    String query = "SELECT EXISTS(SELECT 1 FROM pg_database WHERE datname = '%s')".formatted(dbName);
    Boolean databaseExists = jdbcTemplate.queryForObject(query, Boolean.class);
    log.info("Database exists: {}", databaseExists);
    if (Boolean.FALSE.equals(databaseExists)) {
      String createDbSql = "CREATE DATABASE " + dbName;
      jdbcTemplate.execute(createDbSql);
      log.info("Database {} created successfully.", dbName);
    } else {
      log.info("Database {} already exists.", dbName);
    }

    String env = profile.equals("preview") ? "localhost" : "postgres";
    String url = "jdbc:postgresql://%s:5432/%s".formatted(env, dbName);
    log.info("Database url: {}", url);
    return url;
    // jdbc:postgresql://postgres:5432/postgres
  }
}