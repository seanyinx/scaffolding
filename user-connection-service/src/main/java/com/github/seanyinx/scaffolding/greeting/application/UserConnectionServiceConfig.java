package com.github.seanyinx.scaffolding.greeting.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class UserConnectionServiceConfig {

  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
