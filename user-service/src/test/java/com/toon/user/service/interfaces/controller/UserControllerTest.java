package com.github.seanyinx.user.service.interfaces.controller;

import static com.seanyinx.github.unit.scaffolding.Randomness.uniquify;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.seanyinx.user.service.application.UserService;
import com.github.seanyinx.user.service.application.UserView;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

  private final UserView jack = new UserView(1L, uniquify("jack"));

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Test
  public void ableToGetUserById() throws Exception {
    when(userService.user(1L)).thenReturn(Optional.of(jack));
    mockMvc.perform(get("/users/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username", is(jack.username())));
  }

  @Test
  public void notFoundIfNoSuchUser() throws Exception {
    when(userService.user(1L)).thenReturn(Optional.empty());
    mockMvc.perform(get("/users/{id}", 1L))
        .andExpect(status().isNotFound());
  }
}