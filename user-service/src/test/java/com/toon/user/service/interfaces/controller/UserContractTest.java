package com.github.seanyinx.user.service.interfaces.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import com.github.seanyinx.user.service.application.UserService;
import com.github.seanyinx.user.service.application.UserView;
import java.util.Optional;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@RunWith(PactRunner.class)
@PactFolder("../target/pacts")
@Provider("UserService")
public class UserContractTest {

  private static ConfigurableApplicationContext context;
  private static UserService userService;

  @TestTarget
  public final Target target = new HttpTarget(8081);

  @BeforeClass
  public static void startUserService() {
    context = SpringApplication
        .run(UserApplication.class, "--server.port=8081", "--spring.main.web-environment=true");

    userService = context.getBean(UserService.class);
  }

  @AfterClass
  public static void tearDown() {
    context.close();
  }

  @State("User Sean exists")
  public void retrieveExistingUser() {
    when(userService.user(1L)).thenReturn(Optional.of(new UserView(1L, "Sean")));
  }

  @SpringBootApplication
  @EnableAutoConfiguration(exclude = {
      DataSourceAutoConfiguration.class,
      DataSourceTransactionManagerAutoConfiguration.class,
      HibernateJpaAutoConfiguration.class})
  static class UserApplication {

    public static void main(String[] args) {
      SpringApplication.run(UserApplication.class, args);
    }

    @Bean
    UserService userService() {
      return mock(UserService.class);
    }
  }
}
