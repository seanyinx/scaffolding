package com.github.seanyinx.user.service.application;

import com.github.seanyinx.user.service.domain.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Optional<UserView> user(long id) {
    return userRepository.findById(id)
        .map(user -> new UserView(user.id(), user.username()));
  }
}
