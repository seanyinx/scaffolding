package com.github.seanyinx.scaffolding.greeting.infrastructure.domain;

import com.github.seanyinx.scaffolding.greeting.domain.Connection;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionMapper {

  int insertAndReturnId(Connection connection);

  Connection findById(@Param("id") Long id);

  List<Connection> findConnections();
}

