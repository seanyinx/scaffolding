package com.github.seanyinx.user.service.application;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class UserView {

  private final long id;
  private final String username;

  @JsonCreator
  public UserView(@JsonProperty("id") long id, @JsonProperty("username") String username) {
    this.id = id;
    this.username = username;
  }

  public long id() {
    return id;
  }

  public String username() {
    return username;
  }
}
