package com.github.seanyinx.scaffolding.greeting.infrastructure.domain;

import com.github.seanyinx.scaffolding.greeting.domain.Connection;
import com.github.seanyinx.scaffolding.greeting.domain.UserConnectionRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class UserConnectionRepositoryImpl implements UserConnectionRepository {

  private final ConnectionMapper connectionMapper;

  private final UserMapper userMapper;

  @Autowired
  public UserConnectionRepositoryImpl(ConnectionMapper connectionMapper,
      UserMapper userMapper) {
    this.connectionMapper = connectionMapper;
    this.userMapper = userMapper;
  }

  @Override
  public Connection persist(Connection connection) {
    connectionMapper.insertAndReturnId(connection);
    userMapper.insert(connection.originator());
    userMapper.insert(connection.recipient());
    return connection;
  }

  @Override
  public Optional<Connection> findById(long id) {
    Connection connection = connectionMapper.findById(id);
    String originatorName = userMapper.selectUsernameById(connection.originator().userId());
    String recipientName = userMapper.selectUsernameById(connection.recipient().userId());
    connection.originator().setUsername(originatorName);
    connection.recipient().setUsername(recipientName);
    return Optional.ofNullable(connection);
  }
}
