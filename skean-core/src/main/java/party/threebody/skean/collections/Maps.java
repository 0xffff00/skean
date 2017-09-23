package party.threebody.skean.collections;

import java.util.*;

/**
 * complement some functions found in neither Google Guava nor Apache Common
 * Collections
 *
 * @author hzk
 * @since 2017-08-05
 */
public class Maps {

    private Maps() {

    }

    public static <K, V> Map<K, V> rebuild(Map<K, V> unfiltered, K[] keysLeft) {
        if (unfiltered == null || keysLeft == null) {
            return null;
        }
        Map<K, V> res = new HashMap<>(keysLeft.length * 6 / 5);
        for (int i = 0; i < keysLeft.length; i++) {
            res.put(keysLeft[i], unfiltered.get(keysLeft[i]));
        }
        return res;
    }

    public static <K, V> Map<K, V> rebuild(Map<K, V> unfiltered, Collection<K> keysLeft) {
        if (unfiltered == null || keysLeft == null) {
            return null;
        }
        Map<K, V> res = new HashMap<>(keysLeft.size() * 6 / 5);
        for (K k : keysLeft) {
            res.put(k, unfiltered.get(k));
        }
        return res;
    }

    public static <K, V> Map<K, V> of() {
        return Collections.emptyMap();
    }

    public static <K, V> Map<K, V> of(K k1, V v1) {
        return Collections.singletonMap(k1, v1);
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2) {
        final Map<K, V> res = new LinkedHashMap<>(4);
        res.put(k1, v1);
        res.put(k2, v2);
        return res;
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
        final Map<K, V> res = new LinkedHashMap<>(4);
        res.put(k1, v1);
        res.put(k2, v2);
        res.put(k3, v3);
        return res;
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        final Map<K, V> res = new LinkedHashMap<>(4);
        res.put(k1, v1);
        res.put(k2, v2);
        res.put(k3, v3);
        res.put(k4, v4);
        return res;
    }

    /**
     * the result's size is determined by keys'size
     * Examples:<br>
     * <li>ofKeysAndVals(['a','b'], [1,2,3]) == { 'a':1, 'b':2 }</li>
     * <li>ofKeysAndVals([], [1,2,3]) == {}</li>
     * <li>ofKeysAndVals(null, [1,2,3]) == {}</li>
     * <li>ofKeysAndVals(['a','b'], [1]) == { 'a':1, 'b':null }</li>
     * <li>ofKeysAndVals(['a','b'], []) == { 'a':null, 'b':null }</li>
     * <li>ofKeysAndVals(['a','b'], null) == { 'a':null, 'b':null }</li>
     */
    public static <K, V> Map<K, V> ofKeysAndVals(K[] keys, V[] vals) {
        if (keys == null) {
            return Collections.emptyMap();
        }
        int keysCnt = keys.length;
        int valsCnt = vals == null ? 0 : vals.length;
        final Map<K, V> res = new LinkedHashMap<>(keysCnt);
        for (int i = 0; i < keysCnt; i++) {
            if (i < valsCnt) {
                res.put(keys[i], vals[i]);
            } else {
                res.put(keys[i], null);
            }
        }
        return res;
    }
}
