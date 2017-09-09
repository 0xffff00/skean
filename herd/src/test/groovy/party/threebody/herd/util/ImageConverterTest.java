package party.threebody.herd.util;

import org.junit.Test;

public class ImageConverterTest {
    @Test
    public void calculateScaling() throws Exception {
    }

    @Test
    public void toSimpleFractionAsPossible() throws Exception {
    }

    @Test
    public  void calcRatio() {
        testCalcS(5184,3456,1000,1500);
        testCalcS(4000,3000,1000,1500);
        testCalcS(4208,3120,1000,1500);
        testCalcS(1280,853,1000,1500);
        testCalcS(5184,3456,720,960);
        testCalcS(4000,3000,720,960);
        testCalcS(4208,3120,720,960);
        testCalcS(1280,853,720,960);
        testCalcS(12840,8523,720,960);
        testCalcS(128404,85235,720,960);
        testCalcS(4648404,852355,720,960);
        testCalcS(753,675,720,960);
        testCalcS(432,124,720,960);

    }

    void testCalcS(int w, int h, int min,int max){
        double r=ImageConverter.toJPG().edgeNoLessThan(min).edgeNoMoreThan(max).calculateScaling(w,h);
        System.out.printf("(%d,%d),e in[%d,%d]-> (%.1f,%.1f)=%fMP, s=%f\n",w,h,min,max,w*r,h*r,r*r*w/1000*h/1000,r);
    }

}