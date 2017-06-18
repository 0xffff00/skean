package party.threebody.skean.core.query;

public class BasicCriterion implements Criterion {

	private String name;
	private String operator;
	private Object value;

	public BasicCriterion(String name, Object value) {
		this(name, null, value);
	}

	public BasicCriterion(String name, String operator, Object value) {
		this.name = name;
		this.operator = operator;
		this.value = value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getOperator() {
		return operator;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "SimpleCriterionImpl{name:" + name + ", operator:" + operator + ", value:" + value + "}";
	}

	
	

	
	
}