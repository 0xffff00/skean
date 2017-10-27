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

package party.threebody.skean.lang;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.text.SimpleDateFormat;

public class ObjectMappers {

    public static final ObjectMapper OM_SNAKE_CASE = new ObjectMapper();
    public static final ObjectMapper DEFAULT = new ObjectMapper();

    static {
        OM_SNAKE_CASE.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        OM_SNAKE_CASE.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OM_SNAKE_CASE.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        DEFAULT.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        DEFAULT.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        DEFAULT.registerModule(new JavaTimeModule());
        DEFAULT.configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, false);
        DEFAULT.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        DEFAULT.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    }
}
