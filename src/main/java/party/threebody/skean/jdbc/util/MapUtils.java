package party.threebody.skean.jdbc.util;

import java.util.Map;

public class MapUtils {
	/**
	 * get value filtered by given keys
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> V[] getValsArr(Map<K, V> map, K[] keys) {
		if (map == null || keys == null) {
			return null;
		}
		int n = keys.length;
		V[] vals = (V[]) new Object[n];
		for (int i = 0; i < n; i++) {
			vals[i] = map.get(keys[i]);
		}
		return vals;
	}
}