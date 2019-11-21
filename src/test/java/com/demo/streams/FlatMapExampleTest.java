package com.demo.streams;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static junit.framework.TestCase.assertNull;

public class FlatMapExample {

    @Test
    public void testFlatMap(){
        List<User> users = Arrays.asList(
                new User("Peter",20,Arrays.asList("1","2")),
                new User("Ryan",20,Arrays.asList("3","4")),
                new User("Sam",20,Arrays.asList("5","6")),
                new User("Greg",20,Arrays.asList("7","8"))
        );

        Optional<String> optionalString = users.stream()
                .map(user -> user.getPhone().stream())
                .flatMap(stringStream -> stringStream.filter(phoneNo -> phoneNo.equals("9")))
                .findAny();
      //  optionalString.ifPresentOrElse(System.out::println,System.out::println);
        assertNull(optionalString);
    }
}
