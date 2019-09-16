package com.github.seanyinx.scaffolding.greeting.domain;

import java.util.Optional;

public interface UserConnectionRepository {

  Connection persist(Connection connection);

  Optional<Connection> findById(long id);
}
