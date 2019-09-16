package com.github.seanyinx.scaffolding.greeting.infrastructure.domain;

import com.github.seanyinx.scaffolding.greeting.domain.User;
import com.github.seanyinx.scaffolding.greeting.domain.UserRepository;
import java.util.List;
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
  public void persist(User user) {
    User selectUser = userMapper.selectByUserId(user.userId());
    if (selectUser == null) {
      userMapper.insert(user);
    } else {
      userMapper.update(user);
    }
  }

  @Override
  public List<Long> getAllUserIds() {
    List<Long> ids = userMapper.selectAllUserIds();
    return ids;
  }

}
