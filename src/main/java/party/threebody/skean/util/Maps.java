package party.threebody.skean.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * complement some functions found in neither Google Guava nor Apache Common
 * Collections
 * 
 * @author hzk
 * @since 2017-08-05
 */
public class Maps {

	private Maps() {

	}

	public static <K, V> Map<K, V> rebuild(Map<K, V> unfiltered, K[] keysLeft) {
		if (unfiltered == null || keysLeft == null) {
			return null;
		}
		Map<K, V> res = new HashMap<>(keysLeft.length * 6 / 5);
		for (int i = 0; i < keysLeft.length; i++) {
			res.put(keysLeft[i], unfiltered.get(keysLeft[i]));
		}
		return res;
	}

	public static <K, V> Map<K, V> rebuild(Map<K, V> unfiltered, Collection<K> keysLeft) {
		if (unfiltered == null || keysLeft == null) {
			return null;
		}
		Map<K, V> res = new HashMap<>(keysLeft.size() * 6 / 5);
		for (K k : keysLeft) {
			res.put(k, unfiltered.get(k));
		}
		return res;
	}
}
