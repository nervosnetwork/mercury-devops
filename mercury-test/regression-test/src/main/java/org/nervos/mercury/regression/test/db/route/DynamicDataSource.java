package org.nervos.mercury.regression.test.db.route;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

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
    return dataSource;
  }

  @PostConstruct
  private void initDataSource() {
    Map<Object, Object> dataSources = new HashMap<>(2);
    dataSources.put(
        DataSourceType.RAW_DATA_SOURCE.name(),
        createDataSource(
            driverClassName,
            rawDataSourceConfig.getUrl(),
            rawDataSourceConfig.getUsername(),
            rawDataSourceConfig.getPassword()));
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
