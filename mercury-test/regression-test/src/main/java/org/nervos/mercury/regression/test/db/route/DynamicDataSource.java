package org.nervos.mercury.regression.test.db.route;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration("dataSource")
public class DynamicDataSource extends AbstractRoutingDataSource {

  @Autowired private TestDataSourceConfig testDataSourceConfig;
  @Autowired private RawDataSourceConfig rawDataSourceConfig;

  private Map<Object, Object> targetDataSources;

  private String driverClassName = "org.postgresql.Driver";

  @Override
  protected Object determineCurrentLookupKey() {
    String dataSourceName = DbContextHolder.getDBType();
    return dataSourceName;
  }

  @Override
  public void setTargetDataSources(Map<Object, Object> targetDataSources) {
    this.targetDataSources = targetDataSources;
    super.setTargetDataSources(this.targetDataSources);
    afterPropertiesSet();
  }

  private DataSource createDataSource(
      String driverClassName, String url, String username, String password) {

    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setDriverClassName(driverClassName);
    dataSource.setJdbcUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);

    dataSource.setMaximumPoolSize(50);

    return dataSource;
  }

  public void putDataSource(String url, String username, String password) {
    DataSource dataSource = createDataSource(driverClassName, url, username, password);
    targetDataSources.put(DataSourceType.RAW_DATA_SOURCE.name(), dataSource);
    this.afterPropertiesSet();
  }

  public void removeDataSource() {
    targetDataSources.remove(DataSourceType.RAW_DATA_SOURCE.name());
    this.afterPropertiesSet();
  }

  @PostConstruct
  private void initDataSource() {
    Map<Object, Object> dataSources = new HashMap<>(2);
    dataSources.put(
        DataSourceType.TEST_DATA_SOURCE.name(),
        createDataSource(
            driverClassName,
            testDataSourceConfig.getUrl(),
            testDataSourceConfig.getUsername(),
            testDataSourceConfig.getPassword()));
    setTargetDataSources(dataSources);
  }
}
