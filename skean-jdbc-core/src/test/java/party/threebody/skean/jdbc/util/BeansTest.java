package party.threebody.skean.jdbc.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import party.threebody.skean.lang.Beans;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

public class BeansTest {

    static Car car1;
    @BeforeClass
    public static void prepareAll(){
        car1=new Car("norm","noG","noS","noGS");
        car1.setIntArr(new int[]{1,4,3});
        car1.setIntx(new BigDecimal(1123));
        car1.setLdt(LocalDateTime.now());
        car1.setX(122);
    }
    @Test
    public void testConvertBeanToMap() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        System.out.println(Beans.convertBeanToMap(car1));
        System.out.println(Beans.convertBeanToSimpleMap(car1));
        System.out.println(PropertyUtils.describe(car1));
        System.out.println(BeanUtils.describe(car1));
    }


    @Test
    public void testIsSimpleType() throws Exception{
        String  s1=null;
        String s2="";
        int x1=0;
        Integer ox1=null;
        //assertTrue(s1 instanceof String);
        assertTrue(s2 instanceof String);

    }


}