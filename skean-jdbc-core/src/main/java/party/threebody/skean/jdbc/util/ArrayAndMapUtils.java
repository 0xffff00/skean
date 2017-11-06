/*
 * Copyright 2017-present  Skean Project Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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