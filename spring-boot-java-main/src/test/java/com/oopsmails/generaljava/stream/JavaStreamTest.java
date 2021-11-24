package com.oopsmails.generaljava.stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JavaStreamTest {
    private static final List<String> testList = new ArrayList<>();

    @BeforeAll
    public static void beforeAllTest() {
        System.out.println("in @BeforeAll");
        for (int i = 0; i < 50; i++) {
            testList.add("" + i);
        }
    }

    /**
     * https://www.geeksforgeeks.org/difference-between-collection-stream-foreach-and-collection-foreach-in-java/
     * <p>
     * Collection.forEach() and Collection.stream().forEach() are used for iterating over the
     * collections, there is no such major difference between the two, as both of them give
     * the same result, though there are some differences in their internal working.
     * <p>
     * Collection.stream().forEach() is basically used for iteration in a group of objects by
     * converting a collection into the stream and then iterate over the stream of collection.
     * While iterating over the collection, if any structural changes are made to collections,
     * then it will throw the concurrent modification exception.
     * <p>
     * Collection.forEach() uses the collection’s iterator(whichever is specified). The majority
     * of the collections do not allow modifications in the structure while iterating over that
     * collection. If any changes happen to that collection, i.e. addition of an element or the
     * removal of the element, then they will throw the concurrent modification error.
     * If collection.forEach() is iterating over a synchronized collection, then they will lock
     * the segment of collection and hold it over all the calls that can be made w.r.t to that
     * collection.
     */
    @Test
    void testStreamForEach() throws Exception {
        testList.forEach(System.out::println);
        System.out.println("==================================");
        testList.stream().forEach(System.out::println);
        assertNotNull(testList);
    }

    /**
     * map() vs flatMap()
     * <p>
     * for a Map we have a list of elements and a (function,action) f so :
     * [a,b,c] f(x) => [f(a),f(b),f(c)]
     * <p>
     * and for the flat map we have a list of elements list and we have a (function,action) f and we want the result to be flattened :
     * [[a,b],[c,d,e]] f(x) =>[f(a),f(b),f(c),f(d),f(e)]
     * <p>
     * Optional<Optional<String>> result = Optional.of(42)
     * .map(id -> findById(id));
     * <p>
     * Optional<String> result = Optional.of(42)
     * .flatMap(id -> findById(id));
     * where:
     * <p>
     * private Optional<String> findById(Integer id)
     */

    @Test
    void testMapFlatMap() throws Exception {
        List<String> collected = Stream.of("a", "b", "hello") // Stream of String
                .map(String::toUpperCase) // Returns a stream consisting of the results of applying the given function to the elements of this stream.
                .collect(Collectors.toList());
        assertEquals(asList("A", "B", "HELLO"), collected);

        List<Integer> together = Stream.of(asList(1, 2), asList(3, 4)) // Stream of List<Integer>
                .flatMap(List::stream)
                .map(integer -> integer + 1)
                .collect(Collectors.toList());
        assertEquals(asList(2, 3, 4, 5), together);
    }
}
