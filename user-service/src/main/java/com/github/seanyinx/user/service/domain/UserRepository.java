package com.github.seanyinx.user.service.domain;

import java.util.Optional;

public interface UserRepository {

  Optional<User> findById(long id);
}
