package party.threebody.skean.mvc.util;

import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import party.threebody.skean.SkeanException;
import party.threebody.skean.core.query.BasicCriterion;

public class CriterionBuildUtils {
	private CriterionBuildUtils() {
	}

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
			return objectMapper.readerFor(BasicCriterionVO[].class).readValue(criteria);
		} catch (IOException e) {
			throw new SkeanException("IO error occurred in buildBasicCriterionArray", e);
		}
	}

}

class BasicCriterionVO extends BasicCriterion {

	public void setO(String o) {
		setOperator(o);
	}

	public void setN(String n) {
		setName(n);
	}

	public void setV(Object v) {
		setValue(v);
	}

	public String getO() {
		return getOperator();
	}

	public String getN() {
		return getName();
	}

	public Object getV() {
		return getValue();
	}

}