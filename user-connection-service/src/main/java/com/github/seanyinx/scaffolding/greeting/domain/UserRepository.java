package com.github.seanyinx.scaffolding.greeting.domain;

import java.util.List;

public interface UserRepository {

  void persist(User user);

  List<Long> getAllUserIds();
}
