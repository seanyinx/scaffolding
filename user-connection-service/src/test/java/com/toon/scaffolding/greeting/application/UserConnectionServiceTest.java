package com.github.seanyinx.scaffolding.greeting.application;

import static com.seanyinx.github.unit.scaffolding.Randomness.nextId;
import static com.seanyinx.github.unit.scaffolding.Randomness.uniquify;
import static com.github.seanyinx.scaffolding.greeting.domain.Relationship.FRIEND;
import static java.util.Collections.singletonList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.seanyinx.scaffolding.greeting.domain.Connection;
import com.github.seanyinx.scaffolding.greeting.domain.User;
import com.github.seanyinx.scaffolding.greeting.domain.UserConnectionRepository;
import com.github.seanyinx.scaffolding.greeting.domain.UserRepository;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

public class UserConnectionServiceTest {

  private final RestTemplate restTemplate = mock(RestTemplate.class);
  private final UserConnectionRepository connectionRepository = mock(UserConnectionRepository.class);
  private final UserRepository userRepository = Mockito.mock(UserRepository.class);
  private final UserConnectionService connectionService = new UserConnectionService(
      restTemplate,
      connectionRepository,
      userRepository,
      "http://user-service");

  @Test
  public void persistsNewConnection() {
    ArgumentCaptor<Connection> argumentCaptor = ArgumentCaptor.forClass(Connection.class);

    User originator = new User(nextId(), uniquify("originator"));
    User recipient = new User(nextId(), uniquify("recipient"));
    Connection connection = new Connection(originator, recipient, FRIEND);

    when(connectionRepository.persist(argumentCaptor.capture())).thenReturn(connection);

    connectionService.createFriendConnection(originator, recipient);

    Connection created = argumentCaptor.getValue();
    assertThat(created.originator(), is(originator));
    assertThat(created.recipient(), is(recipient));
    assertThat(created.relationship(), is(FRIEND));
  }

  @Test
  public void fetchUserFromRemote() {
    User someUser = new User(1L, uniquify("someUser"));

    when(userRepository.getAllUserIds()).thenReturn(singletonList(1L));
    when(restTemplate.getForObject("http://user-service/users/{id}", User.class, 1L)).thenReturn(someUser);

    connectionService.pollUserUpdates();

    verify(userRepository).persist(someUser);
  }
}