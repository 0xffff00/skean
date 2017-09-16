package party.threebody.skean.jdbc;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Collections;

public class DualColsBeanSerializer extends StdSerializer<DualColsBean> {
    public DualColsBeanSerializer(){
        this(DualColsBean.class);
    }
    public DualColsBeanSerializer(Class<DualColsBean> clazz) {
        super(clazz);
    }

    @Override
    public void serialize(DualColsBean value, JsonGenerator gen, SerializerProvider provider) throws IOException {

        gen.writeStartArray();
        gen.writeObject(value.get0());
        gen.writeObject(value.get1());
        gen.writeObject(Collections.singletonMap("kkk",333));
        gen.writeEndArray();
    }
}
