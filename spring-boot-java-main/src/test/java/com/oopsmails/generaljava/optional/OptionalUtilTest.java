package com.oopsmails.generaljava.optional;

import com.oopsmails.common.tool.optional.OptionalUtil;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OptionalUtilTest {

    private static final String DEFAULT_VALUE = "defaultValue";
    private static final String HELLO_WORLD = "Hello World";

    @Test
    void testUsingOptionalUtil() {
        // Example objects
        Obj1 obj1 = new Obj1(new Obj2(new Obj3(HELLO_WORLD)));
        Obj1 nullObj1 = null;
        Obj1 nullObj12 = new Obj1(null);
        Obj1 nullObj13 = new Obj1(new Obj2(null));

        String result1 = Optional.ofNullable(obj1)
                .map(Obj1::getNestedObj2)
                .map(Obj2::getNestedObj3)
                .map(Obj3::getValue)
                .orElse(DEFAULT_VALUE);

        System.out.println("Without utility method (verbose), Result1: " + result1); // Output: Hello World
        assertEquals(HELLO_WORLD, result1);

        // With utility method (neater, with a default value)
        String result2 = OptionalUtil.mapNested(obj1,
                o -> o.getNestedObj2().getNestedObj3().getValue(),
                DEFAULT_VALUE);

        System.out.println("Result2: " + result2); // Output: Hello World
        assertEquals(HELLO_WORLD, result2);

        // Handling null values with default
        String result3 = OptionalUtil.mapNested(nullObj1,
                o -> o.getNestedObj2().getNestedObj3().getValue(),
                DEFAULT_VALUE);

        System.out.println("Result3: " + result3); // Output: Default Value
        assertEquals(DEFAULT_VALUE, result3);

        String result4 = OptionalUtil.mapNested(nullObj12,
                o -> o.getNestedObj2().getNestedObj3().getValue(),
                DEFAULT_VALUE);

        System.out.println("Result4: " + result4); // Output: Default Value
        assertEquals(DEFAULT_VALUE, result4);

        String result5 = OptionalUtil.mapNested(nullObj13,
                o -> o.getNestedObj2().getNestedObj3().getValue(),
                DEFAULT_VALUE);

        System.out.println("Result5: " + result5); // Output: Default Value
        assertEquals(DEFAULT_VALUE, result5);
    }

    class Obj1 {
        private final Obj2 nestedObj2;

        public Obj1(Obj2 nestedObj2) {
            this.nestedObj2 = nestedObj2;
        }

        public Obj2 getNestedObj2() {
            return nestedObj2;
        }
    }

    class Obj2 {
        private final Obj3 nestedObj3;

        public Obj2(Obj3 nestedObj3) {
            this.nestedObj3 = nestedObj3;
        }

        public Obj3 getNestedObj3() {
            return nestedObj3;
        }
    }

    class Obj3 {
        private final String value;

        public Obj3(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
