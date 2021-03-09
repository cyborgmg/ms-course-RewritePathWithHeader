package com.devsuperior.hrapigatewayzuul;

import com.devsuperior.hrapigatewayzuul.filter.RewritePathWithHeader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class HrApiGatewayZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrApiGatewayZuulApplication.class, args);
	}

	@Bean
	public RewritePathWithHeader simpleFilter() {
		return new RewritePathWithHeader();
	}

}
