package com.github.seanyinx.user.service.infrastructure.domain;

import com.github.seanyinx.user.service.domain.User;
import com.github.seanyinx.user.service.domain.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class UserRepositoryImpl implements UserRepository {

  private final UserMapper userMapper;

  @Autowired
  public UserRepositoryImpl(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  @Override
  public Optional<User> findById(long id) {
    User user = userMapper.selectByPrimaryKey(id);
    return Optional.ofNullable(user);
  }
}
