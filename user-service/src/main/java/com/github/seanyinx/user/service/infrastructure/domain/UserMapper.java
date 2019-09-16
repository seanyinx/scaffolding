package com.github.seanyinx.user.service.infrastructure.domain;

import com.github.seanyinx.user.service.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

  User selectByPrimaryKey(Long id);
}

