package ru.inex.accepter.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.inex.parameters.repository.BaseParametersRepository;
import ru.inex.parameters.service.BaseParametersService;

/**
 * Описание конфгурации подсоединения к БД MAIN-DB Postgress
 */
@Configuration
public class DBConfig {

  @Value("${CONFIG_DB_URL}")
  private String configDatabaseURL;

  @Value("${CONFIG_DB_LOGIN}")
  private String configDatabaseLogin;

  @Value("${CONFIG_DB_PASSWORD}")
  private String configDatabasePassword;

  @Value("${CIPHER_KEY}")
  private String cipherKey;

  @Bean(destroyMethod = "close")
  public HikariDataSource configDbDataSource() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setPoolName("Main-DB");
    hikariConfig.setJdbcUrl(configDatabaseURL);
    hikariConfig.setUsername(configDatabaseLogin);
    hikariConfig.setPassword(configDatabasePassword);
    hikariConfig.setMaximumPoolSize(10);
    hikariConfig.setMinimumIdle(2);
    hikariConfig.setIdleTimeout(600000);
    hikariConfig.setLeakDetectionThreshold(30000);
    hikariConfig.setConnectionTimeout(20000);
    hikariConfig.setConnectionTestQuery("select 1");
    return new HikariDataSource(hikariConfig);
  }

  @Bean
  public BaseParametersService baseParametersService() {
    return new BaseParametersService(baseParametersRepository(), cipherKey);
  }
  private BaseParametersRepository baseParametersRepository() {
    return new BaseParametersRepository(new JdbcTemplate(configDbDataSource()));
  }
}