package com.github.seanyinx.scaffolding.greeting.interfaces.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.github.seanyinx.scaffolding.greeting.application.UserConnectionService;
import com.github.seanyinx.scaffolding.greeting.domain.Connection;
import com.github.seanyinx.scaffolding.greeting.domain.FriendRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequestMapping("/connections")
public class UserConnectionController {

  private final UserConnectionService connectionService;

  @Autowired
  public UserConnectionController(UserConnectionService connectionService) {
    this.connectionService = connectionService;
  }

  @PostMapping(value = "", consumes = APPLICATION_JSON_VALUE)
  ResponseEntity<String> createConnection(@RequestBody FriendRequest friendRequest) {
    Connection connection = connectionService.createFriendConnection(
        friendRequest.originator(),
        friendRequest.recipient());

    return ResponseEntity.created(
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(connection.id())
            .toUri())
        .body("Created");
  }

  @GetMapping("/{id}")
  ResponseEntity<Connection> connection(@PathVariable long id) {
    return connectionService.connection(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
