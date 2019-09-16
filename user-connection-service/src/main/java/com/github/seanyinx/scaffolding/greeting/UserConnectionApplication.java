package com.github.seanyinx.scaffolding.greeting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootConfiguration
@ComponentScan(
    excludeFilters = {@ComponentScan.Filter(
        type = FilterType.CUSTOM,
        classes = {TypeExcludeFilter.class}
    ), @ComponentScan.Filter(
        type = FilterType.CUSTOM,
        classes = {AutoConfigurationExcludeFilter.class}
    )}
)
@EnableScheduling
public class UserConnectionApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserConnectionApplication.class, args);
  }
}

