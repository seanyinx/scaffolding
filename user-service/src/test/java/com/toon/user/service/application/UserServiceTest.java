package com.github.seanyinx.user.service.application;

import static com.seanyinx.github.unit.scaffolding.Randomness.uniquify;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import com.github.seanyinx.user.service.domain.User;
import com.github.seanyinx.user.service.domain.UserRepository;
import java.util.Optional;
import org.junit.Test;
import org.mockito.Mockito;

public class UserServiceTest {

  private final UserRepository userRepository = Mockito.mock(UserRepository.class);
  private final UserService userService = new UserService(userRepository);
  private final User user = new User(1L, uniquify("username"));

  @Test
  public void getUserWithRepository() {
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));

    UserView user = userService.user(1L).orElse(null);

    assertThat(user.id(), is(this.user.id()));
    assertThat(user.username(), is(this.user.username()));
  }
}