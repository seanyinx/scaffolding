package com.github.seanyinx.scaffolding.greeting.application;

import com.github.seanyinx.scaffolding.greeting.domain.Connection;
import com.github.seanyinx.scaffolding.greeting.domain.User;
import com.github.seanyinx.scaffolding.greeting.domain.UserConnectionRepository;
import com.github.seanyinx.scaffolding.greeting.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

import static com.github.seanyinx.scaffolding.greeting.domain.Relationship.FRIEND;

@Service
public class UserConnectionService {

  private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private final RestTemplate restTemplate;
  private final UserConnectionRepository connectionRepository;
  private final UserRepository userRepository;

  private final String userServiceUrl;

  @Autowired
  UserConnectionService(RestTemplate restTemplate,
      UserConnectionRepository connectionRepository,
      UserRepository userRepository,
      @Value("${user.service.url}") String userServiceUrl) {

    this.restTemplate = restTemplate;
    this.connectionRepository = connectionRepository;
    this.userRepository = userRepository;
    this.userServiceUrl = userServiceUrl;
  }


  public String getUrl() {
    return userServiceUrl;
  }

  public Connection createFriendConnection(User originator, User recipient) {
    return connectionRepository.persist(new Connection(originator, recipient, FRIEND));
  }

  public Optional<Connection> connection(long id) {
    return connectionRepository.findById(id);
  }

  @Scheduled(initialDelayString = "${user.update.interval:5000}", fixedDelayString = "${user.update.interval:5000}")
  public void pollUserUpdates() {
    LOG.info("Polling user service at {} for user updates", userServiceUrl);
    userRepository.getAllUserIds()
        .forEach(id -> userRepository.persist(
            restTemplate.getForObject(userServiceUrl + "/users/{id}", User.class, id)));
  }
}
