package com.github.seanyinx.user.service;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

import com.github.seanyinx.user.service.application.UserView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class UserApplicationTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void ableToGetUserById() {
    ResponseEntity<UserView> responseEntity = restTemplate.getForEntity("/users/{id}", UserView.class, 1L);

    assertThat(responseEntity.getStatusCode(), is(OK));

    UserView user = responseEntity.getBody();
    assertThat(user.username(), is("jack"));
  }
}
