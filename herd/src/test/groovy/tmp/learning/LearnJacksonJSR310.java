package tmp.learning;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;

public class LearnJacksonJSR310 {

    public static void main(String[] args){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        String now = null;
        try {
            now = mapper.writeValueAsString(Arrays.asList("aaa", LocalDateTime.now()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(now);
    }
}
