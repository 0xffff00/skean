package party.threebody.skean.core.query;

/**
 * 通用查询条件准则<br>
 * 
 * @author hzk
 * @since 2017-06-17
 */
public interface Criterion {

	void setName(String name);

	String getName();

	void setValue(Object value);

	Object getValue();
}