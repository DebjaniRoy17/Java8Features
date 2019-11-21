package com.demo.streams;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;

@RunWith(JUnit4.class)
public class FilterExamplesTest {

    @Test
    public void testFilter(){
        List<String> names = Arrays.asList("Peter","Sam","Greg","Ryan");
        System.out.println(names.toString());
        Stream nameList = names.stream()
                .filter(name -> !("Sam").equalsIgnoreCase(name))
               ;
        assertEquals(3,nameList.count());
    }
}
