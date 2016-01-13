package com.example.controllers;

import com.example.models.Person;
import com.example.models.Things;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.core.diff.changetype.container.ListChange;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Hello {

  @RequestMapping("/hello")
  Map<String, Object> hello() {

    Javers javers = JaversBuilder.javers().build();

    Person tommyOld = new Person("tommy", "Tommy Smart", 0);
    Person tommyNew = new Person("tommy", "Tommy C. Smart", 100);

    Things oldThingA = new Things("test A", "test A.one", 1);
    Things oldThingB = new Things("test B", "test A.two", 2);
    Things oldThingC = new Things("test C", "test A.three", 3);
    Things newThingA = new Things("test A", "test B.one", 1);
    Things newThingB = new Things("test B", "test B.two", 2);

    tommyOld.addThings(oldThingA);
    tommyOld.addThings(oldThingB);
    tommyOld.addThings(oldThingC);
    tommyNew.addThings(newThingA);
    tommyNew.addThings(newThingB);

    Diff diff = javers.compare(tommyOld, tommyNew);

    System.out.println(diff.prettyPrint());

    Map<String, Object> diffs = new HashMap<>();

    diff.getChangesByType(ValueChange.class).forEach(change -> {
      Map<String, Object> val = new HashMap<>();
      val.put("left", change.getLeft());
      val.put("right", change.getRight());
      String str = change.getAffectedGlobalId().toString();
      if(str.contains("#")) {
        String key = String.join("", str.substring(str.indexOf('#') + 1), " ", "-", " ", change.getPropertyName());
        diffs.put(key, val);
      }
      else {
        diffs.put(change.getPropertyName(), val);
      }
    });

    diff.getChangesByType(ListChange.class).forEach(change -> {
      List<String> val = new ArrayList<>();
      change.getChanges().forEach(sub -> {
        val.add(sub.toString());
      });
      diffs.put(change.getPropertyName(), val);
    });

    return diffs;
  }
}
