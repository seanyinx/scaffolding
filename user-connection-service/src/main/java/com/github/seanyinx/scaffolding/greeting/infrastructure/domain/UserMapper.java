package com.github.seanyinx.scaffolding.greeting.infrastructure.domain;

import com.github.seanyinx.scaffolding.greeting.domain.User;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

  int insert(User user);

  List<Long> selectAllUserIds();

  String selectUsernameById(@Param("userId") long userId);

  User selectByUserId(@Param("userId") long userId);

  int update(User user);
}

