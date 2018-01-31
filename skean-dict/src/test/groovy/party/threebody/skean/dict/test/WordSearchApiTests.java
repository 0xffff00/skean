package party.threebody.skean.dict.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.junit4.SpringRunner;
import party.threebody.skean.dict.domain.BasicRelation;
import party.threebody.skean.dict.domain.X1Relation;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class WordSearchApiTests {
    @Autowired TestRestTemplate restTemplate;

    @Test
    public void test1() throws Exception {
        List<BasicRelation> brels= Arrays.asList(
                buildBR("北京人", "INST", "李雷"),
                buildBR("aa2z schoolmate", "INST", "韩梅梅"),
                buildBR("李雷","INST","Lily")
        );
        List<X1Relation> x1rels= Arrays.asList(
                buildX1R("aa2z", "schoolmate", "李雷"),
                buildX1R("aa2z", "schoolmate", "韩梅梅"),
                buildX1R("aa2z", "schoolmate", "Lily"),
                buildX1R("李雷","motherschool","aa2z")
        );
        for (BasicRelation brel:brels){
            restTemplate.exchange(RequestEntity.post(new URI("/relations/basic"))
                    .accept(APPLICATION_JSON).body(brel), BasicRelation.class);
        }
//        UriComponentsBuilder.fromOriginHeader("")
//        restTemplate.

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
}
