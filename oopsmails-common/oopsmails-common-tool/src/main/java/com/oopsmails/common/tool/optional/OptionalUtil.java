package com.oopsmails.common.tool.optional;

import java.util.function.Supplier;

/**
 * Ref: https://stackoverflow.com/questions/3458451/check-chains-of-get-calls-for-null
 */
@Deprecated
public class OptionalUtil {
    /**
     * Using this to avoid null check chain
     *
     * NOTE: This omits NullPointerException
     *
     * e.g, house.getFloor(0).getWall(WEST).getDoor().getDoorknob();
     *
     * To avoid a NullPointerException, I'd have to do the following if:
     *
     * if (house != null && house.getFloor(0) && house.getFloor(0).getWall(WEST) != null
     *   && house.getFloor(0).getWall(WEST).getDoor() != null) ...
     *
     * Doorknob knob = getFieldValue(() -> house.getFloor(0).getWall(WEST).getDoor().getDoorknob());
     *
     * 6
     * No doubt that it is a really tricky way to handle this case but it is a very bad practice to handle null pointer exception as you might end up handling something unintended. You can read the relevant parts of effective java to get a better understanding. –
     * MD. Sahib Bin Mahboob
     *  CommentedJul 9, 2020 at 8:35
     * 7
     * Isn't NullpointerException expensive than a null check ? –
     * user3044440
     *  CommentedDec 11, 2020 at 21:21
     * 2
     * Don't resort to this unless you are using a really old version of Java (<8 where Optional isn't available). See the answer involving Optional.ofNullable()/.map() –
     * Michael Peterson
     *  CommentedApr 3, 2023 at 1:31
     * This approach is effective when you have a complex data structure, where the majority of the data is optional. Like accessing nested JAXB xml elements. 2 things though: Using .getFloor(0), I'd also handle IndexOutOfBoundsException the same way. Returning with an Optional<T> would be more elegant. –
     * sanya
     *  CommentedAug 25, 2023 at 12:38
     *
     * @param statement
     * @param <T>
     * @return
     */
    public static <T> T getFieldValue(Supplier<T> statement, T defaultValue) {
        try {
            return statement.get();
        } catch (NullPointerException exc) {
            return defaultValue;
        }
    }
}
