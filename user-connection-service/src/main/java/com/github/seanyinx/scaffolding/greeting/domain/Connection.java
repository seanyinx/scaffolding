package com.github.seanyinx.scaffolding.greeting.domain;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class Connection {

  private Long id;

  private User originator;

  private User recipient;

  private Relationship relationship;

  private Connection() {
  }

  public Connection(User originator, User recipient, Relationship relationship) {
    this.originator = originator;
    this.recipient = recipient;
    this.relationship = relationship;
  }

  public Connection(long id, User originator, User recipient, Relationship relationship) {
    this.id = id;
    this.originator = originator;
    this.recipient = recipient;
    this.relationship = relationship;
  }

  public long id() {
    return id;
  }

  public User originator() {
    return originator;
  }

  public User recipient() {
    return recipient;
  }

  public Relationship relationship() {
    return relationship;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getOriginator() {
    return originator;
  }

  public void setOriginator(User originator) {
    this.originator = originator;
  }

  public User getRecipient() {
    return recipient;
  }

  public void setRecipient(User recipient) {
    this.recipient = recipient;
  }

  public Relationship getRelationship() {
    return relationship;
  }

  public void setRelationship(Relationship relationship) {
    this.relationship = relationship;
  }
}
