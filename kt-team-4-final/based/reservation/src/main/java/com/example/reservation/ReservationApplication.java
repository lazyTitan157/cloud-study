package com.example.reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@EnableBinding(Processor.class)
public class ReservationApplication {

	public static ApplicationContext applicationContext;
	
	public static void main(String[] args) {
		applicationContext = SpringApplication.run(ReservationApplication.class, args);
	}
	
	@StreamListener(Processor.INPUT)
	public void notting() {
		
	}
	
	/**
	 * 1. RestTemplate 을 쓰려면 spring-boot-starter-web 디펜던시를 추가하고, 아래를 추가하는 방법이 있음 
	 */
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	/**
	 * 2. RestTemplate 의 PATCH를 쓰려면 httpClient 디펜던시를 추가하여 아래와 같이 구현  
	 */
//	@Bean
//	RestTemplate restTemplate() {
//		RestTemplate restTemplate = new RestTemplate();
//		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//		converter.setObjectMapper(new ObjectMapper());
//		restTemplate.getMessageConverters().add(converter);
//		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
//		restTemplate.setRequestFactory(requestFactory);
//		return restTemplate;
//	}

}
