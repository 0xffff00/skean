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

package party.threebody.skean.data.result;

import java.util.*;
import java.util.stream.Collectors;

public interface Counts {

    //------ static builders ------

    static Count empty(){
        return  of((String)null);
    }

    static Count of(Class<?> entityClazz) {
        return of(entityClazz.getName());
    }

    static Count of(String entityName) {
        return of(entityName, null);
    }

    static Count of(String entityName, String entitiesName) {
        Count r = new Count();
        r.entityName = entityName;
        r.entitiesName = entitiesName;
        return r;
    }

    static Count append(String action, int num) {
        return new Count().append(action, num);
    }

    static Count created(int num) {
        return new Count().created(num);
    }

    static Count updated(int num) {
        return new Count().updated(num);
    }

    static Count deleted(int num) {
        return new Count().deleted(num);
    }

    static Count completed(int num) {
        return new Count().completed(num);
    }

    static Count failed(int num) {
        return new Count().failed(num);
    }

    static Count skipped(int num) {
        return new Count().skipped(num);
    }

    static Count createdOne() {
        return created(1);
    }

    static Count updatedOne() {
        return updated(1);
    }

    static Count completedOne() {
        return completed(1);
    }

    static Count deletedOne() {
        return deleted(1);
    }

    static Count failedOne() {
        return failed(1);
    }

    static Count skippedOne() {
        return skipped(1);
    }

    //----- calculations -------

    /**
     * merge by grouping entityName
     */
    static List<Count> merge(Count... counts) {
        return merge(Arrays.asList(counts));
    }

    /**
     * merge by grouping entityName
     */
    static List<Count> merge(Collection<Count> counts) {
        Map<String, Optional<Count>> map = counts.stream().collect(
                Collectors.groupingBy(
                        Count::getEntityName,
                        Collectors.reducing(Count::add)
                )
        );
        return map.values().stream().map(c -> c.orElse(null)).collect(Collectors.toList());
    }
}
