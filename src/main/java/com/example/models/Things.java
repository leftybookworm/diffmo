package com.example.models;

public class Things {

  private String value;
  private Integer id;

  public Things(String login, String name, Integer id) {
    this.value = name;
    this.id = id;
  }

  public Integer getId() { return id; }

  public String getValue() { return value; }
}