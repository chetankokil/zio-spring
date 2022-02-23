package com.example.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import javax.sql.DataSource
import org.springframework.boot.jdbc.DataSourceBuilder

@Configuration
class AppConfig {

  @Bean
  def dataSource(): DataSource = {
    DataSourceBuilder
      .create()
      .username("postgres")
      .password("")
      .url("jdbc:postgres://localhost:5432/userdata")
      .driverClassName("org.postgresql.Driver")
      .build();
  }

}
