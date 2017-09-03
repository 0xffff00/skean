package party.threebody.skean.mvc.generic;

import org.junit.Test;

import java.util.stream.IntStream;

import static java.util.stream.IntStream.range;
import static org.junit.Assert.*;

public class AffectCountTest {

    @Test
    public void
    test1000Adds(){
        AffectCount affectCount=IntStream.range(0,1000)
                .mapToObj(i->AffectCount.ofOnlyCreated(i*15%9971))
                .reduce(AffectCount::add)
                .orElse(AffectCount.NOTHING);
        System.out.println(affectCount.numCreated);
        System.out.println(affectCount);
        System.out.println(String.join(",","1","a","","2","",""));

    }
}