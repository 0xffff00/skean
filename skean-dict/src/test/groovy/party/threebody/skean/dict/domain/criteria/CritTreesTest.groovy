package party.threebody.skean.dict.domain.criteria

import org.junit.Test
import party.threebody.skean.lang.ObjectMappers

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

class CritTreesTest {
    @Test
    public void testJsonDeser() throws Exception {
        def s1 = '{"m": "attr", "x": "star", "ch": [{ "o": "GT", "v": 3},{ "o": "LT", "v": 5}]}'
        def s2 = """
[
  { "o": "KL", "v": "AAA"},
  {"f":"instOf", "v":"bbb"},
  {"m": "attr", "x": "mos", "ch":[$s1]},
  { "m": "sups", "o0":"K","v0": "HHH"}
]"""

        CritTreeNode n1 = ObjectMappers.DEFAULT.readValue(s1, CritTreeNode.class)
        assertEquals 'star', n1.x
        def n2 = CritTrees.fromJson(s2)
        assertEquals 4, n2.size()
        assertTrue n2[0] instanceof TextFilterNode
        assertTrue n2[1] instanceof RelFilterNode
        assertTrue n2[2] instanceof MapNode
        assertTrue n2[3] instanceof MapNode
        MapNode n23 = n2[3]
        assertEquals MapNode.Mapper.sups, n23.mapper
        assertEquals 1, n23.children.size()
        assertTrue n23.children[0] instanceof TextFilterNode
        TextFilterNode n230 = n23.children[0]
        assertEquals "K", n230.opt
        assertEquals "HHH", n230.val

    }
}
