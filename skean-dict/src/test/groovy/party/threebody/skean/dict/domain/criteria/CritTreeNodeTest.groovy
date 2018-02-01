package party.threebody.skean.dict.domain.criteria

import com.fasterxml.jackson.core.type.TypeReference
import org.junit.Test
import party.threebody.skean.lang.ObjectMappers

import static org.junit.Assert.*

class CritTreeNodeTest {
    @Test
    public void testJsonDeser() throws Exception {
        def s1 = '{"t": "attr", "v": "star", "ch": [{ "o": "GT", "v": 3},{ "o": "LT", "v": 5}]}'
        def s2 = """
[
  { "o": "KL", "v": "AAA"},
  {"t":"instOf", "v":"bbb"},
  {"t": "attr", "v": "mos", "ch":[$s1]}
]"""
        CritTreeNode n1 = ObjectMappers.DEFAULT.readValue(s1, CritTreeNode.class)
        assertEquals 'star',n1.val
        List n2 = ObjectMappers.DEFAULT.readValue(s2, new TypeReference<List<CritTreeNode>>() {})
        assertEquals 3,n2.size()
        assertTrue n2[0] instanceof TextFilterNode
        assertTrue n2[1] instanceof RelFilterNode
        assertTrue n2[2] instanceof MapNode
    }
}
