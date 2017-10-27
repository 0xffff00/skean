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

package party.threebody.skean.jdbc.rs;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class TripleColsBeanSerializer extends StdSerializer<TripleColsBean> {
    public TripleColsBeanSerializer() {
        this(TripleColsBean.class);
    }

    public TripleColsBeanSerializer(Class<TripleColsBean> clazz) {
        super(clazz);
    }

    @Override
    public void serialize(TripleColsBean value, JsonGenerator gen, SerializerProvider provider) throws IOException {

        gen.writeStartArray();
        gen.writeObject(value.get0());
        gen.writeObject(value.get1());
        gen.writeObject(value.get2());
        gen.writeEndArray();
    }
}
