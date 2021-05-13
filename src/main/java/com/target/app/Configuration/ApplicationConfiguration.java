package com.target.app.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.concurrent.Executor;

/**
 * Application configuration class
 */
@Configuration
@EnableAsync
public class ApplicationConfiguration {

    @Value("${restTemplate.connection.timeout}")
    private int connectionTimeout;

    @Value("${restTemplate.read.timeout}")
    private int readTimeout;

   @Bean
   public RestTemplate restTemplate()
   {
       RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

       restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(connectionTimeout));
       restTemplateBuilder.setReadTimeout(Duration.ofSeconds(readTimeout));

       return restTemplateBuilder.build();
   }

   @Bean
   public Executor taskExecutor() {
       ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
       executor.setCorePoolSize(2);
       executor.setMaxPoolSize(2);
       executor.setQueueCapacity(500);
       executor.setThreadNamePrefix("RedskyServiceThread-");
       executor.initialize();
       return executor;
   }
}
