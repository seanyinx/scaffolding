package com.github.seanyinx.scaffolding.greeting.application;

import static java.util.Collections.singletonList;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.seanyinx.scaffolding.greeting.domain.User;
import com.github.seanyinx.scaffolding.greeting.domain.UserConnectionRepository;
import com.github.seanyinx.scaffolding.greeting.domain.UserRepository;
import java.util.HashMap;
import java.util.Map;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

public class UserContractTest {

  @Rule
  public final PactProviderRule providerRule = new PactProviderRule("UserService", this);

  private final User sean = new User(1L, "Sean");
  private final ObjectMapper objectMapper = new ObjectMapper();

  private final UserConnectionRepository connectionRepository = mock(UserConnectionRepository.class);
  private final UserRepository userRepository = Mockito.mock(UserRepository.class);
  private final RestTemplate restTemplate = new RestTemplate();

  private final UserConnectionService connectionService = new UserConnectionService(
      restTemplate,
      connectionRepository,
      userRepository,
      providerRule.getConfig().url());

  @Pact(consumer = "UserConnectionService")
  public PactFragment createFragment(PactDslWithProvider pactDslWithProvider) throws JsonProcessingException {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", APPLICATION_JSON_VALUE);

    return pactDslWithProvider
        .given("User Sean exists")
        .uponReceiving("a request to get details of Sean")
        .path("/users/1")
        .method("GET")
        .willRespondWith()
        .headers(headers)
        .status(HttpStatus.OK.value())
        .body(objectMapper.writeValueAsString(sean))
        .toFragment();
  }

  @PactVerification
  @Test
  public void validatesUserToken() {
    when(userRepository.getAllUserIds()).thenReturn(singletonList(1L));

    connectionService.pollUserUpdates();

    verify(userRepository).persist(argThat(isUser(sean)));
  }

  private ArgumentMatcher<User> isUser(final User sean) {
    return user -> user.toString().equals(sean.toString());
  }
}