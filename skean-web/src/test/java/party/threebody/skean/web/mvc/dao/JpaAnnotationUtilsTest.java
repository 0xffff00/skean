package party.threebody.skean.web.mvc.dao;

import org.junit.Test;
import party.threebody.skean.data.Column;
import party.threebody.skean.data.LastUpdateTime;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class JpaAnnotationUtilsTest {

    @Test
    public void test1() {
//        System.out.println(Arrays.toString(Car.class.getFields()));
//        System.out.println(Arrays.toString(Car.class.getDeclaredFields()));
//        System.out.println(Arrays.toString(RaceCar.class.getFields()));
//        System.out.println(Arrays.toString(RaceCar.class.getDeclaredFields()));
//        System.out.println(Beans.getAllDeclaredFields(RaceCar.class));
//        System.out.println("--------");

        assertEquals(3, JpaCrudDAO.JpaAnnotationUtils.fetchFieldNamesByAnnotated(
                Car.class, Column.class, LocalDateTime.class).size());
        assertEquals(4, JpaCrudDAO.JpaAnnotationUtils.fetchFieldNamesByAnnotated(
                RaceCar.class, Column.class, LocalDateTime.class).size());
    }

    public static class Car {
        @Column String brand;
        @Column String brand2;
        @LastUpdateTime LocalDateTime produceTime;

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public LocalDateTime getProduceTime() {
            return produceTime;
        }

        public void setProduceTime(LocalDateTime produceTime) {
            this.produceTime = produceTime;
        }
    }

    public static class RaceCar extends Car {
        @Column String racer;

    }
}
