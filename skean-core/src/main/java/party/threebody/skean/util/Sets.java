package party.threebody.skean.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * complement some functions found in neither Google Guava nor Apache Common Collections
 *
 * @author hzk
 * @since 2017-09-08
 */
public class Sets {
    private Sets() {

    }

    /**
     *  seems more efficient via https://stackoverflow.com/questions/3064423/how-to-convert-an-array-to-a-set-in-java
     * @param elements
     * @param <E>
     * @return
     */
    public static <E> Set<E> of(E... elements){
        return new HashSet<E>(Arrays.asList(elements));
    }
}
