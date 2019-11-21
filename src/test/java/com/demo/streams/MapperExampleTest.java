package com.demo.streams;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;


public class MapperExample {

    @Test
    public void testMapper(){
        List<String> names = Arrays.asList("Peter","Sam","Greg","Ryan");

        List<User> nameList = names.stream()
                .filter(name -> !("Sam").equalsIgnoreCase(name))
                .map(User::new)
                .collect(Collectors.toList());
        assertTrue(nameList.contains("Peter"));
    }

    @Test
    public void testMaptoInt(){
        List<String> names = Arrays.asList("Peter","Sam","Greg","Ryan");

        List<User> nameList = names.stream()
                .filter(name -> !("Sam").equalsIgnoreCase(name))
                .map(User::new)
                .collect(Collectors.toList());
        int sum = nameList.stream().mapToInt(User::getAge).sum();
        assertEquals(6,sum);
    }
}
