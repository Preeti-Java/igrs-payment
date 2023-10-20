package com.cg.neel.igrs.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(
		exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class}
		)
@EnableJpaRepositories("com.cg.neel.igrs.*")
@ComponentScans(value = {
		@ComponentScan("com.cg.neel.igrs.*"),
		})
@EntityScan("com.cg.neel.igrs.*")   
@EnableEurekaClient
@EnableFeignClients 
public class IgrsPaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(IgrsPaymentApplication.class, args);
	}

	@LoadBalanced // adding this line solved the issue
	@Bean
	 RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
}
