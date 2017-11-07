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

import java.util.Arrays;
import java.util.Collection;
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
     * seems more efficient via https://stackoverflow.com/questions/3064423/how-to-convert-an-array-to-a-set-in-java
     *
     * @param elements
     * @param <E>
     * @return
     */
    public static <E> Set<E> of(E... elements) {
        return new HashSet<>(Arrays.asList(elements));
    }

    public static <E> Set<E> of(Collection<E>... elements) {
        Set set = new HashSet<E>();
        for (Collection<E> e : elements) {
            set.addAll(e);
        }
        return set;
    }

}
