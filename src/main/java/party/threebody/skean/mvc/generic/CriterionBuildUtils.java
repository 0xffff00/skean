package party.threebody.skean.mvc.generic;

import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import party.threebody.skean.SkeanException;
import party.threebody.skean.core.query.BasicCriterion;

public class CriterionBuildUtils {
	private static ObjectMapper objectMapper;
	static {
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static BasicCriterion[] buildBasicCriterionArray(String criteria) {
		if (criteria == null) {
			return null;
		}
		try {
			return objectMapper.readerFor(BasicCriterion[].class).readValue(criteria);
		} catch (IOException e) {
			throw new SkeanException(e);
		}
	}

}

class BasicCriterionWithBrief extends BasicCriterion {

	public BasicCriterionWithBrief(String name, String operator, Object value) {
		super(name, operator, value);
	}

	void setO(String o) {
		setOperator(o);
	}

	void setN(String n) {
		setName(n);
	}

	void setV(String v) {
		setValue(v);
	}

}