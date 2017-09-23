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
