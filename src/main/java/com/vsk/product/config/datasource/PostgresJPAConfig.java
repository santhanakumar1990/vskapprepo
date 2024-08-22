package com.vsk.product.config.datasource;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.vsk.product.config.datasource.properties.DatasourceAdditionalProperties;
import com.vsk.product.config.datasource.properties.DatasourceProperties;

@Configuration
@EnableJpaRepositories(basePackages = "com.vsk.product.entity.repository", entityManagerFactoryRef = "postgresEntityManagerFactory", transactionManagerRef = "postgresTransactionManager")
public class PostgresJPAConfig {

	@Bean
	@ConfigurationProperties(prefix = "postgres.datasource")
	public DatasourceProperties postgresProperties() {
		return new DatasourceProperties();
	}

	@Bean
	@ConfigurationProperties(prefix = "postgres.properties")
	public DatasourceAdditionalProperties postgresAdditionalProperties() {
		return new DatasourceAdditionalProperties();
	}

	@Bean
	public DataSource postgresDatasource(@Qualifier("postgresProperties") DatasourceProperties dataSourceProperties) {

		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName(dataSourceProperties.getDriverClassName());
		dataSourceBuilder.username(dataSourceProperties.getUsername());
		dataSourceBuilder.password(dataSourceProperties.getPassword());
		dataSourceBuilder.url(dataSourceProperties.getUrl());
		return dataSourceBuilder.build();

	}

	@Bean
	public LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory(
			EntityManagerFactoryBuilder entityManagerFactoryBuilder,
			@Qualifier("postgresDatasource") DataSource dataSource,
			@Qualifier("postgresAdditionalProperties") DatasourceAdditionalProperties additionalProperties) {
		Map<String, String> hibernateProperties = new HashMap<String, String>();
		hibernateProperties.put("hibernate.hbm2ddl.auto", additionalProperties.getDdlAuto());
		hibernateProperties.put("hibernate.dialect", additionalProperties.getDialect());
		return entityManagerFactoryBuilder.dataSource(dataSource).packages("com.vsk.product.entity")
				.properties(hibernateProperties).build();
	}

	@Bean
	public PlatformTransactionManager postgresTransactionManager(
			@Qualifier("postgresEntityManagerFactory") LocalContainerEntityManagerFactoryBean efb) {
		return new JpaTransactionManager(efb.getObject());
	}

}
