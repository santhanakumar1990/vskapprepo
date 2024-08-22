package com.vsk.product.config.loadbalance;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.DummyPing;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;

//@Configuration
public class RibbonConfiguration {

	@Bean
	public IPing ribbonPing() {
		return new DummyPing();
	}
	@Bean
	public IRule ribbonRule() {
		return new RoundRobinRule();
	}
}
