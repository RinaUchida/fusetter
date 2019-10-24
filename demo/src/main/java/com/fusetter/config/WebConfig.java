package com.fusetter.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class WebConfig {

	@Autowired
	private Environment environment;

	@Bean
	public DataSource readOnlyDataSource() {
		String baseConfig = "spring.datasource.readOnly.%s";
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(environment.getProperty(String.format(baseConfig, "driverClassName")));
		config.setJdbcUrl(environment.getProperty(String.format(baseConfig, "url")));
		config.setUsername(environment.getProperty(String.format(baseConfig, "username")));
		config.setPassword(environment.getProperty(String.format(baseConfig, "password")));
		HikariDataSource dataSource = new HikariDataSource(config);
		dataSource.setMinimumIdle(
				Integer.parseInt(environment.getProperty(String.format(baseConfig, "minIdlePoolSize"))));
		dataSource.setMaximumPoolSize(
				Integer.parseInt(environment.getProperty(String.format(baseConfig, "maximumPoolSize"))));
		dataSource.setIdleTimeout(Long.parseLong(environment.getProperty(String.format(baseConfig, "idleTimeout_ms"))));
		dataSource.setMaxLifetime(Long.parseLong(environment.getProperty(String.format(baseConfig, "maxLifetime_ms"))));
		return dataSource;
	}

	@Bean()
	@Primary
	public DataSource updatableDataSource() {
		String baseConfig = "spring.datasource.updatable.%s";
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(environment.getProperty(String.format(baseConfig, "driverClassName")));
		config.setJdbcUrl(environment.getProperty(String.format(baseConfig, "url")));
		config.setUsername(environment.getProperty(String.format(baseConfig, "username")));
		config.setPassword(environment.getProperty(String.format(baseConfig, "password")));
		HikariDataSource dataSource = new HikariDataSource(config);
		dataSource.setMinimumIdle(
				Integer.parseInt(environment.getProperty(String.format(baseConfig, "minIdlePoolSize"))));
		dataSource.setMaximumPoolSize(
				Integer.parseInt(environment.getProperty(String.format(baseConfig, "maximumPoolSize"))));
		dataSource.setIdleTimeout(Long.parseLong(environment.getProperty(String.format(baseConfig, "idleTimeout_ms"))));
		dataSource.setMaxLifetime(Long.parseLong(environment.getProperty(String.format(baseConfig, "maxLifetime_ms"))));
		return dataSource;
	}

}
