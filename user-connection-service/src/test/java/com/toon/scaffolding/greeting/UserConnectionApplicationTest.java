package com.github.seanyinx.scaffolding.greeting;

import com.github.seanyinx.scaffolding.greeting.domain.Connection;
import com.github.seanyinx.scaffolding.greeting.domain.User;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.seanyinx.scaffolding.greeting.domain.Relationship.FRIEND;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, properties = {"user.update.interval=100", "user.service.url=http://localhost:8082"},
    classes = UserConnectionApplication.class)
@ActiveProfiles(profiles = {"test"})
public class UserConnectionApplicationTest {

  @ClassRule
  public static final WireMockRule wireMockRule = new WireMockRule(8082);

  @Autowired
  private TestRestTemplate restTemplate;

  @BeforeClass
  public static void setUp() {
    stubFor(get(urlEqualTo("/users/1"))
        .willReturn(
            aResponse()
                .withHeader(CONTENT_TYPE, APPLICATION_JSON.getMimeType())
                .withStatus(SC_OK)
                .withBody("{\n" +
                    "  \"id\": 1,\n" +
                    "  \"username\": \"jacky\"\n" +
                    "}")));

    stubFor(get(urlEqualTo("/users/2"))
        .willReturn(
            aResponse()
                .withHeader(CONTENT_TYPE, APPLICATION_JSON.getMimeType())
                .withStatus(SC_OK)
                .withBody("{\n" +
                    "  \"id\": 2,\n" +
                    "  \"username\": \"rosetta\"\n" +
                    "}")));
  }

  @Test
  public void ableToGetConnectionById() throws InterruptedException {
    restTemplate.postForEntity("/connections",
        new Connection(new User(1L, "jack"), new User(2L, "rose"), FRIEND), String.class);

    ResponseEntity<Connection> responseEntity = restTemplate.getForEntity("/connections/{id}", Connection.class, 1L);

    assertThat(responseEntity.getStatusCode(), is(OK));

    Connection connection = responseEntity.getBody();
    assertThat(connection.originator().username(), is("jack"));
    assertThat(connection.recipient().username(), is("rose"));

    // wait for polling user update
    Thread.sleep(200);

    responseEntity = restTemplate.getForEntity("/connections/{id}", Connection.class, 1L);

    assertThat(responseEntity.getStatusCode(), is(OK));

    connection = responseEntity.getBody();
    assertThat(connection.originator().username(), is("jacky"));
    assertThat(connection.recipient().username(), is("rosetta"));
  }
}
