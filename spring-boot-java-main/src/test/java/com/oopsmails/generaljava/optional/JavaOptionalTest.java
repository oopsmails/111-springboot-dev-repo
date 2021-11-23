package com.oopsmails.generaljava.optional;

import com.oopsmails.common.tool.optional.OptionalUtil;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Search "java 8 optional null check chain"
 * Ref: https://stackoverflow.com/questions/3458451/check-chains-of-get-calls-for-null
 * <p>
 * Optional.ofNullable(house)
 * .map(house -> house.getFloor(0))
 * .map(floorZero -> floorZero.getWall(WEST))
 * .map(wallWest -> wallWest.getDoor())
 * .map(door -> wallWest.getDoor())
 */


public class JavaOptionalTest {

    @Test
    public void testUsingCatch() throws Exception {
        final String EXPECTED = "defaultValue";
        Outer outer = new Outer();
        String foo = OptionalUtil.getFieldValue(() -> outer.getNested().getInner().getFoo(), EXPECTED);
        System.out.println("foo: " + foo);
        assertEquals(EXPECTED, foo);
    }

    @Test
    public void testUsingMap() throws Exception {
        System.out.println("----- Not Initialized! -----");

        Optional.ofNullable(new Outer())
                .map(out -> out.getNested())
                .map(nest -> nest.getInner())
                .map(in -> in.getFoo())
                .ifPresent(foo -> System.out.println("foo: " + foo)); //no print

        System.out.println("----- Let's Initialize! -----");

        Optional.ofNullable(new OuterInit())
                .map(out -> out.getNestedInit())
                .map(nest -> nest.getInnerInit())
                .map(in -> in.getFoo())
                .ifPresent(foo -> System.out.println("foo: " + foo)); //will print!
    }

    public class Outer {
        Nested nested;

        Nested getNested() {
            return nested;
        }
    }

    public class Nested {
        Inner inner;

        Inner getInner() {
            return inner;
        }
    }

    public class Inner {
        String foo = "yeah!";

        String getFoo() {
            return foo;
        }
    }

    public class OuterInit {
        NestedInit nested = new NestedInit();

        NestedInit getNestedInit() {
            return nested;
        }
    }

    public class NestedInit {
        InnerInit inner = new InnerInit();

        InnerInit getInnerInit() {
            return inner;
        }
    }

    public class InnerInit {
        String foo = "yeah!";

        String getFoo() {
            return foo;
        }
    }
}
