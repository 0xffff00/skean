package party.threebody.skean.collections;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MapsTest {
    @Test
    public void ofKeysAndVals() throws Exception {
        Map<String, Integer> map;
        map = Maps.ofKeysAndVals(new String[]{"a", "b"}, new Integer[]{1, 2, 3});
        assertArrayEquals(new String[]{"a", "b"}, map.keySet().toArray());
        assertArrayEquals(new Integer[]{1, 2}, map.values().toArray());

        map = Maps.ofKeysAndVals(new String[]{}, new Integer[]{1, 2, 3});
        assertEquals(0, map.size());
        map = Maps.ofKeysAndVals(null, new Integer[]{1, 2, 3});
        assertEquals(0, map.size());
        map = Maps.ofKeysAndVals(null, null);
        assertEquals(0, map.size());

        map = Maps.ofKeysAndVals(new String[]{"a", "b"}, new Integer[]{1});
        assertEquals(2, map.size());
        assertEquals(Integer.valueOf(1), map.get("a"));
        assertEquals(null, map.get("b"));

        map = Maps.ofKeysAndVals(new String[]{"a", "b"}, null);
        assertEquals(2, map.size());
        assertEquals(null, map.get("a"));
        assertEquals(null, map.get("b"));
    }

}