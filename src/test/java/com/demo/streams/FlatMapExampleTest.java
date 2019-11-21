package com.demo.streams;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

public class FlatMapExampleTest {

    @Test
    public void testFlatMap(){
        List<UserTest> users = Arrays.asList(
                new UserTest("Peter",20,Arrays.asList("1","2")),
                new UserTest("Ryan",20,Arrays.asList("3","4")),
                new UserTest("Sam",20,Arrays.asList("5","6")),
                new UserTest("Greg",20,Arrays.asList("7","8"))
        );

        Optional<String> optionalString = users.stream()
                .map(user -> user.getPhone().stream())
                .flatMap(stringStream -> stringStream.filter(phoneNo -> phoneNo.equals("9")))
                .findAny();
      optionalString.ifPresent(System.out::println);
        assertEquals(Optional.empty(),optionalString);
    }
}
