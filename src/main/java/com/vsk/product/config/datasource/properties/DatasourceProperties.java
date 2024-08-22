package com.vsk.product.config.datasource.properties;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DatasourceProperties {

	private String url;
	private String username;
	private String password;
	private String driverClassName;
	private String platform;

}
