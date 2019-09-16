package com.github.seanyinx.scaffolding.greeting.domain;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class User {

  private long id;
  private String username;

  private User() {
  }

  @JsonCreator
  public User(@JsonProperty("id") long userId, @JsonProperty("username") String username) {
    this.id = userId;
    this.username = username;
  }

  public long userId() {
    return id;
  }

  public String username() {
    return username;
  }

  @Override
  public String toString() {
    return "User{" +
        "userId=" + id +
        ", username='" + username + '\'' +
        '}';
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
