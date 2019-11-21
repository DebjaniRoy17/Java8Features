package com.demo.streams;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


public class MapperExampleTest {

    @Test
    public void testMapper(){
        List<String> names = Arrays.asList("Peter","Sam","Greg","Ryan");

        List<UserTest> nameList = names.stream()
                .filter(name -> !("Sam").equalsIgnoreCase(name))
                .map(UserTest::new)
                .collect(Collectors.toList());
        assertEquals(1,nameList.stream().filter(name -> name.getName().contains("Peter")).count());
    }

    @Test
    public void testMaptoInt(){
        List<String> names = Arrays.asList("Peter","Sam","Greg","Ryan");

        List<UserTest> nameList = names.stream()
                .filter(name -> !("Sam").equalsIgnoreCase(name))
                .map(UserTest::new)
                .collect(Collectors.toList());
        int sum = nameList.stream().mapToInt(UserTest::getAge).sum();
        assertEquals(6,sum);
    }
}
