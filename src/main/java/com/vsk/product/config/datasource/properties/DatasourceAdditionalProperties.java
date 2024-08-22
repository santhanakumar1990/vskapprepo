package com.vsk.product.config.datasource.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DatasourceAdditionalProperties {

	private String ddlAuto;
	private String dialect;

}
