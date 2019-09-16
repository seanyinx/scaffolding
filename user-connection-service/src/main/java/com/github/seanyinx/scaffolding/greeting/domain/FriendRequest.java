package com.github.seanyinx.scaffolding.greeting.domain;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class FriendRequest {

  private final User originator;
  private final User recipient;

  @JsonCreator
  public FriendRequest(@JsonProperty("originator") User originator, @JsonProperty("recipient") User recipient) {
    this.originator = originator;
    this.recipient = recipient;
  }

  public User originator() {
    return originator;
  }

  public User recipient() {
    return recipient;
  }
}
