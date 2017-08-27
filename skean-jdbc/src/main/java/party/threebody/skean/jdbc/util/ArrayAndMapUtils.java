package party.threebody.skean.jdbc.util;

import java.util.Map;

public class ArrayAndMapUtils {
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

	/**
	 * get a sub array which remove all null elements
	 */
	@SuppressWarnings("unchecked")
	public static <E> E[] toNonNullArr(E[] src) {
		int k = 0;
		for (int i = 0; i < src.length; i++) {
			if (src[i] != null) {
				k++;
			}
		}
		Object[] arr = new Object[k];
		for (int i = 0, j = 0; i < src.length; i++) {
			if (src[i] != null) {
				arr[j++] = src[i];
			}
		}
		return (E[]) arr;
	}
}