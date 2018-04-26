package com.streamsoft.currencies.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RestTemplateConfiguration implements WebMvcConfigurer {
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
