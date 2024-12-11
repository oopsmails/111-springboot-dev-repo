package com.oopsmails.common.tool.optional;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Ref: https://stackoverflow.com/questions/3458451/check-chains-of-get-calls-for-null
 */
public class OptionalUtil {
    /**
     * Using this to avoid null check chain
     * <p>
     * NOTE: This omits NullPointerException
     * <p>
     * e.g, house.getFloor(0).getWall(WEST).getDoor().getDoorknob();
     * <p>
     * To avoid a NullPointerException, I'd have to do the following if:
     * <p>
     * if (house != null && house.getFloor(0) && house.getFloor(0).getWall(WEST) != null
     * && house.getFloor(0).getWall(WEST).getDoor() != null) ...
     * <p>
     * Doorknob knob = getFieldValue(() -> house.getFloor(0).getWall(WEST).getDoor().getDoorknob());
     * <p>
     * 6
     * No doubt that it is a really tricky way to handle this case but it is a very bad practice to handle null pointer exception as you might end up handling something unintended. You can read the relevant parts of effective java to get a better understanding. –
     * MD. Sahib Bin Mahboob
     * CommentedJul 9, 2020 at 8:35
     * 7
     * Isn't NullpointerException expensive than a null check ? –
     * user3044440
     * CommentedDec 11, 2020 at 21:21
     * 2
     * Don't resort to this unless you are using a really old version of Java (<8 where Optional isn't available). See the answer involving Optional.ofNullable()/.map() –
     * Michael Peterson
     * CommentedApr 3, 2023 at 1:31
     * This approach is effective when you have a complex data structure, where the majority of the data is optional. Like accessing nested JAXB xml elements. 2 things though: Using .getFloor(0), I'd also handle IndexOutOfBoundsException the same way. Returning with an Optional<T> would be more elegant. –
     * sanya
     * CommentedAug 25, 2023 at 12:38
     *
     * @param statement
     * @param <T>
     * @return
     */
    @Deprecated
    public static <T> T getFieldValue(Supplier<T> statement, T defaultValue) {
        try {
            return statement.get();
        } catch (NullPointerException exc) {
            return defaultValue;
        }
    }

    /**
     * Safely navigates through nested objects using a chain of functions.
     * Returns a default value if any object in the chain is null.
     *
     * @param value        the initial value, may be null
     * @param mapper       a chain of mapping functions to navigate nested fields
     * @param defaultValue the default value to return if the chain resolves to null
     * @param <T>          the type of the initial value
     * @param <R>          the type of the result
     * @return the result of the mapping chain or the default value if null
     */
    public static <T, R> R mapNested(T value, Function<T, R> mapper, R defaultValue) {
        if (value == null) {
            return defaultValue;
        }

        try {
            R result = mapper.apply(value);
            return result != null ? result : defaultValue;
        } catch (NullPointerException e) {
            return defaultValue;
        }
    }
}
