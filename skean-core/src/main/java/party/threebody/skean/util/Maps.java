package party.threebody.skean.util;

import java.util.*;

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

	public static <K,V> Map<K,V> of(){
		return Collections.emptyMap();
	}
	public static <K,V> Map<K,V> of(K k1,V v1){
		return Collections.singletonMap(k1,v1);
	}

	public static <K,V> Map<K,V> of(K k1,V v1,K k2,V v2){
		Map<K,V> res=new LinkedHashMap<>(4);
		res.put(k1,v1);
		res.put(k2,v2);
		return res;
	}
	public static <K,V> Map<K,V> of(K k1,V v1,K k2,V v2,K k3,V v3){
		Map<K,V> res=new LinkedHashMap<>(4);
		res.put(k1,v1);
		res.put(k2,v2);
		res.put(k3,v3);
		return res;
	}

	public static <K,V> Map<K,V> of(K k1,V v1,K k2,V v2,K k3,V v3,K k4,V v4){
		Map<K,V> res=new LinkedHashMap<>(4);
		res.put(k1,v1);
		res.put(k2,v2);
		res.put(k3,v3);
		res.put(k4,v4);
		return res;
	}
}
