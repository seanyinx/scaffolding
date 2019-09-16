package com.github.seanyinx.scaffolding.greeting.interfaces.controller;

import static com.seanyinx.github.unit.scaffolding.Randomness.nextId;
import static com.seanyinx.github.unit.scaffolding.Randomness.uniquify;
import static com.github.seanyinx.scaffolding.greeting.domain.Relationship.FRIEND;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.seanyinx.scaffolding.greeting.application.UserConnectionService;
import com.github.seanyinx.scaffolding.greeting.domain.Connection;
import com.github.seanyinx.scaffolding.greeting.domain.FriendRequest;
import com.github.seanyinx.scaffolding.greeting.domain.User;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(UserConnectionController.class)
public class UserConnectionControllerTest {

  @MockBean
  private UserConnectionService connectionService;

  @Autowired
  private MockMvc mockMvc;

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final User originator = new User(nextId(), uniquify("originator"));
  private final User recipient = new User(nextId(), uniquify("recipient"));
  private final Connection connection = new Connection(1L, originator, recipient, FRIEND);

  @Test
  public void createsNewFriendConnection() throws Exception {
    when(connectionService.createFriendConnection(argThat(isUser(originator)), argThat(isUser(recipient))))
        .thenReturn(connection);

    mockMvc.perform(
        post("/connections")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new FriendRequest(originator, recipient))))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", is("http://localhost/connections/1")));
  }

  @Test
  public void getsNewFriendConnection() throws Exception {
    when(connectionService.connection(1L)).thenReturn(Optional.of(connection));

    mockMvc.perform(get("/connections/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.originator.id", is((int) originator.userId())))
        .andExpect(jsonPath("$.recipient.id", is((int) recipient.userId())));
  }

  @Test
  public void notFoundIfNoSuchConnection() throws Exception {
    when(connectionService.connection(1L)).thenReturn(Optional.empty());

    mockMvc.perform(get("/connections/{id}", 1L))
        .andExpect(status().isNotFound());
  }

  private ArgumentMatcher<User> isUser(User expected) {
    return user -> user.toString().equals(expected.toString());
  }
}