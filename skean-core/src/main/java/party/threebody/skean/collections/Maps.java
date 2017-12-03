/*
 * Copyright 2017-present  Skean Project Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package party.threebody.skean.collections;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    /**
     * no offical syntactic sugar for java 8 stream
     * see: https://stackoverflow.com/questions/24630963/java-8-nullpointerexception-in-collectors-tomap
     */
    public static <K1, V1, K2, V2> Map<K2, V2> rebuild(Map<K1, V1> source,
                                                       Function<K1, K2> keyTransformer,
                                                       Function<V1, V2> valTransformer) {
        if (source == null) {
            return null;
        }
        Map res = new LinkedHashMap(source.size() * 4 / 3);
        for (K1 k1 : source.keySet()) {
            V1 v1 = source.get(k1);
            K2 k2 = keyTransformer.apply(k1);
            V2 v2 = valTransformer.apply(v1);
            res.put(k2, v2);
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

    /**
     * @return a new Map that all null values removed
     */
    public static <K, V> Map<K, V> filterNullValues(Map<K, V> source) {
        return source.entrySet().stream()
                .filter(e -> e.getValue() != null)
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
    }

}
