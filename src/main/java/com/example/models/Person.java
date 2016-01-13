package com.example.models;

import java.util.ArrayList;
import java.util.List;

public class Person {

  private String login;
  private String name;
  private Integer id;
  private List<Things> thingses;

  public Person(String login, String name, Integer id) {
    this.login = login;
    this.name = name;
    this.id = id;
    this.thingses = new ArrayList<>();
  }

  public void addThings(Things thing) {
    thingses.add(thing);
  }

  public List<Things> getThings() { return  thingses; }

  public String getLogin() { return login; }

  public Integer getId() { return id; }

  public String getName() { return name; }
}