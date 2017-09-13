package party.threebody.skean.lang;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class ObjectMappers {

	public static final ObjectMapper OM_SNAKE_CASE = new ObjectMapper();
	public static final ObjectMapper DEFAULT = new ObjectMapper();
	static {
		OM_SNAKE_CASE.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		OM_SNAKE_CASE.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		OM_SNAKE_CASE.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		DEFAULT.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		DEFAULT.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
	}
}
