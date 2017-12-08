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

package party.threebody.skean.web.mvc;

import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MultiValueMaps {

    public static Map<String, Object> toMap(MultiValueMap<String, String> mvmap) {
        if (mvmap == null) {
            return null;
        }
        return mvmap.keySet().stream().collect(Collectors.toMap(
                Function.identity(),
                key -> {
                    List<String> vals = mvmap.get(key);
                    if (vals == null || vals.size() == 0) {
                        return null;
                    } else if (vals.size() == 1) {
                        return vals.get(0);
                    } else {
                        return vals;
                    }
                }
        ));
    }

}
