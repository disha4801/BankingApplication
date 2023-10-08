package com.dnb.apigatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dnb.apigatewayservice.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

	private final JwtAuthenticationFilter filter;

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("AUTH-SERVICE",
						r -> r.path("/api/authenticate/**").filters(f -> f.filter(filter)).uri("lb://AUTH-SERVICE"))
				.route("AUTH-SERVICE",
						r -> r.path("/api/user/**").filters(f -> f.filter(filter)).uri("lb://AUTH-SERVICE"))
				.route("ACCOUNT-SERVICE",
						r -> r.path("/api/account/**").filters(f -> f.filter(filter)).uri("lb://ACCOUNT-SERVICE"))
				.route("LOAN-SERVICE",
						r -> r.path("/api/loan/**").filters(f -> f.filter(filter)).uri("lb://LOAN-SERVICE"))
				.build();
	}
}