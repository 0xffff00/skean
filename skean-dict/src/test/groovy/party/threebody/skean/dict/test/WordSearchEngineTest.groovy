package party.threebody.skean.dict.test

import org.apache.commons.collections4.CollectionUtils
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit4.SpringRunner
import party.threebody.skean.dict.dao.BasicRelationDao
import party.threebody.skean.dict.dao.X1RelationDao
import party.threebody.skean.dict.domain.BasicRelation
import party.threebody.skean.dict.domain.X1Relation
import party.threebody.skean.dict.service.WordSearchEngine
import party.threebody.skean.dict.service.WordService

import static org.junit.Assert.assertTrue
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class WordSearchEngineTest {
    @Autowired TestRestTemplate restTemplate

    @Autowired X1RelationDao x1RelationDao
    @Autowired BasicRelationDao basicRelationDao
    @Autowired WordSearchEngine se
    @Autowired WordService wordService

    @Test
    public void test1() throws Exception {
        List<BasicRelation> brels = Arrays.asList(
                buildBR("中国", "SUBS", "北京"),
                buildBR("中国", "SUBS", "浙江"),
                buildBR("直辖市", "INST", "北京"),
                buildBR("直辖市", "INST", "上海"),
                buildBR("A校k届2班学生", "INST", "李雷"),
                buildBR("A校k届2班学生", "INST", "韩梅梅"),
                buildBR("A校k届2班学生", "INST", "Lily"),
                buildBR("A校k届3班学生", "INST", "Lucy"),
                buildBR("A校学生", "SUBS", "A校k届2班学生"),
                buildBR("A校学生", "SUBS", "A校k届3班学生"),
                buildBR("A校", "SUBT", "A校学生"),
                buildBR("北京学生", "SUBS", "A校学生"),
                buildBR("北京高中", "INST", "A校"),
                buildBR("北京高中", "INST", "B校"),
                buildBR("北京学校", "SUBS", "北京高中"),
                buildBR("中国学校", "SUBS", "北京学校")
        )
        List<X1Relation> x1rels = Arrays.asList(
                buildX1R("李雷", "省籍", "北京"),
                buildX1R("韩梅梅", "省籍", "浙江"),
                buildX1R("李雷", "国籍", "中国"),
                buildX1R("韩梅梅", "国籍", "中国"),
                buildX1R("Lily", "国籍", "英国"),
                buildX1R("Lucy", "国籍", "英国"),
                buildX1R("李雷", "性别", "男"),
                buildX1R("韩梅梅", "性别", "女"),
                buildX1R("Lily", "性别", "女"),
                buildX1R("Lucy", "性别", "女"),
                buildX1R("李雷", "出生年份", "1990"),
                buildX1R("韩梅梅", "出生年份", "1991"),
                buildX1R("Lily", "出生年份", "1991"),
                buildX1R("Lucy", "出生年份", "1993"),
                buildX1R("北京", "GDP省排名", "5"),
                buildX1R("浙江", "GDP省排名", "3"),
                buildX1R("A校", "星级", "5")
        )
        for (BasicRelation br : brels) {
            wordService.createBasicRelation(br);
        }
        for (X1Relation x1r : x1rels) {
            wordService.createX1Relation(x1r);
        }

        // ---test tree.height=1
        assertSetEquals(se.searchByJson('[{"o":"EQ","v":"Lily"}]'), "Lily")
        assertSetEquals(se.searchByJson('[{"o":"K","v":"i"}]'), "Lily")
        assertSetEquals(se.searchByJson('[{"o":"KR","v":"y"}]'), "Lily", "Lucy")
        assertSizeEquals(se.searchByJson('[{"t":"subOf","v":"北京高中"}]'), 9)
        assertSetEquals(se.searchByJson('[{"t":"instOf","v":"北京高中"}]'), "A校", "B校")
        assertSizeEquals(se.searchByJson('[{"t":"subsOf","v":"中国学校"}]'), 2)
        assertSetEquals(se.searchByJson('[{"t":"subtOf","v":"A校"}]'), "A校学生")
        assertSizeEquals(se.searchByJson('[{"t":"instOf","v":"A校学生"}]'), 4)
        // ---test higher tree but no multi children
        assertSetEquals(se.searchByJson('[{"t":"attr","v":"出生年份","ch":[{"o":"NE","v":"1991"}]}]'),
                "李雷", "Lucy")
        // GDP前三名的富省人
        assertSetEquals(se.searchByJson('''
[{"t":"attr","v":"省籍","ch":[
  {"t":"attr","v":"GDP省排名","ch":[{"o":"LE","v":"3"}]}
]}]'''), "韩梅梅")
        // 省籍是直辖市的A校男生
        assertSetEquals(se.searchByJson('''[
{"t":"attr","v":"省籍","ch":[{"t":"instOf","v":"直辖市"}]},
{"t":"attr","v":"性别","ch":[{"o":"EQ","v":"男"}]},
{"t":"instOf","v":"A校学生"}]},
]'''), "李雷")
        // 外国女生
        assertSetEquals(se.searchByJson('''[
{"t":"attr","v":"国籍","ch":[{"o":"NE","v":"中国"}]},
{"t":"attr","v":"性别","ch":[{"o":"EQ","v":"女"}]}
]'''), "Lucy", "Lily")
        // A校k届3班的外国女生
        assertSetEquals(se.searchByJson('''[
{"t":"attr","v":"国籍","ch":[{"o":"NE","v":"中国"}]},
{"t":"attr","v":"性别","ch":[{"o":"EQ","v":"女"}]},
{"t":"instOf","v":"A校k届3班学生"}
]'''), "Lucy")

        // 4星以上的,名字A开头的北京高校的学生
        assertSizeEquals(se.searchByJson('''
[
    {"t":"instOf","v":"北京学生"},
    {"t":"sup","ch":[
        {"t":"attr","v":"星级","ch":[{"o":"GT","v":"4"}]},
        {"o":"KL","v":"A"}
    ]}
]'''), 4)
    }

    static BasicRelation buildBR(String src, String attr, String dst) {
        BasicRelation br1 = new BasicRelation();
        br1.setSrc(src);
        br1.setAttr(attr);
        br1.setDst(dst);
        return br1;
    }

    static X1Relation buildX1R(String src, String attr, String dst) {
        X1Relation x1r = new X1Relation();
        x1r.setSrc(src);
        x1r.setAttr(attr);
        x1r.setDst(dst);
        return x1r;
    }

    static <E> void assertSizeEquals(Collection<E> collection, int expectedSize) {
        assertTrue("Expected: " + expectedSize + " .Actual: " + collection.size() + ", " + collection,
                expectedSize == collection.size());
    }

    static <E> void assertSetEquals(Collection<E> collection, E... expected) {
        List<E> expectedList = Arrays.asList(expected);
        boolean ok = CollectionUtils.isEqualCollection(collection, expectedList);
        assertTrue("Expected: " + expectedList + ". Actual: " + collection, ok);
    }
}
