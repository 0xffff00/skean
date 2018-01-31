package party.threebody.skean.dict.test;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import party.threebody.skean.collections.Maps;
import party.threebody.skean.dict.dao.BasicRelationDao;
import party.threebody.skean.dict.dao.X1RelationDao;
import party.threebody.skean.dict.domain.BasicRelation;
import party.threebody.skean.dict.domain.X1Relation;
import party.threebody.skean.dict.service.WordSearchEngine;
import party.threebody.skean.dict.service.WordService;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class WordSearchApiTests {
    @Autowired TestRestTemplate restTemplate;

    @Autowired X1RelationDao x1RelationDao;
    @Autowired BasicRelationDao basicRelationDao;
    @Autowired WordSearchEngine se;
    @Autowired WordService wordService;

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
                buildBR("北京高中", "INST", "A校"),
                buildBR("北京高中", "INST", "B校"),
                buildBR("北京学校", "SUBS", "北京高中"),
                buildBR("中国学校", "SUBS", "北京学校")
        );
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
                buildX1R("浙江", "GDP省排名", "3")
        );
//        for (BasicRelation br : brels) {
//            restTemplate.exchange(RequestEntity.post(new URI("/relations/basic"))
//                    .accept(APPLICATION_JSON).body(br), BasicRelation.class);
//        }
//        for (X1Relation x1r : x1rels) {
//            restTemplate.exchange(RequestEntity.post(new URI("/relations/x1"))
//                    .accept(APPLICATION_JSON).body(x1r), BasicRelation.class);
        //     }
        for (BasicRelation br : brels) {
            wordService.createBasicRelation(br);
        }
        for (X1Relation x1r : x1rels) {
            wordService.createX1Relation(x1r);
        }

//        assertSetEquals(se.search(Maps.of("text", "Lily")), "Lily");
//        assertSetEquals(se.search(Maps.of("text_K", "i")), "Lily");
//        assertSetEquals(se.search(Maps.of("text_KR", "y")), "Lily", "Lucy");
//        assertSizeEquals(se.search(Maps.of("subOf", "北京高中")), 9);
//        assertSetEquals(se.search(Maps.of("instanceOf", "北京高中")), "A校", "B校");
//        assertSizeEquals(se.search(Maps.of("subsetOf", "中国学校")), 2);
//        assertSetEquals(se.search(Maps.of("subtopicOf", "A校")), "A校学生");
//        assertSizeEquals(se.search(Maps.of("instanceOf", "A校学生")), 4);
        assertSetEquals(se.search(Maps.of("attr^text_NE", "出生年份^1991")),
                "李雷", "Lucy");
        assertSetEquals(se.search(Maps.of("instanceOf", "A校k届3班学生", "attr^text_NE", "出生年份^1991")),
                 "Lucy");
        Maps.of("attr^attr^d_LE","省籍^GDP省排名^3");
        Maps.of("attr^instanceOf^s_K","省籍^^辖");

//        UriComponentsBuilder.fromOriginHeader("")
//        restTemplate.1

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
