package party.threebody.skean.jdbc.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class ValueHolder {
	private boolean valEnabled;
	private Object valObj;
	private Map<String, Object> valMap;
	private Object[] valArr;

	public ValueHolder() {

	}

	public void valArr(Object[] vals) {
		valEnabled = true;
		valArr = vals;
	}

	public void valColl(Collection<Object> vals) {
		valArr(vals.toArray());
	}

	public void valMap(Map<String, Object> vals) {
		valEnabled = true;
		valMap = vals;
	}

	public void valObj(Object vals) {
		valEnabled = true;
		valObj = vals;
	}

	@SuppressWarnings("unchecked")
	public void val(Object[] vals) {
		if (vals == null || vals.length == 0) {
			return;
		}

		if (vals.length == 1) {
			Object v0 = vals[0];
			if (v0 == null) {
				valMap(Collections.emptyMap());
			}
			if (v0 instanceof Map) {
				valMap((Map<String, Object>) v0);
			}

			if (v0 instanceof Collection) {
				valColl((Collection<Object>) v0);
			}
			if (JavaBeans.instanceOfSimpleType(v0)) {
				valArr(vals);
			}
			valObj(v0);
		} else {
			valArr(vals);
		}

	}

	public boolean enabled() {
		return valEnabled;
	}

	public Object getObj() {
		return valObj;
	}

	public Map<String, Object> getMap() {
		return valMap;
	}

	public Object[] getArr() {
		return valArr;
	}

}
