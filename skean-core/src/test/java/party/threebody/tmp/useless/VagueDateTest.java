package party.threebody.tmp.useless;

import org.junit.Test;
import party.threebody.skean.lang.ChronoUnitX;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;

public class VagueDateTest {
    @Test
    public void basicOpts() throws Exception {
        VagueDate vd1 = VagueDate.of(2018, 10);
        assertEquals(LocalDate.of(2018, 10, 1), vd1.begin());
        assertEquals(LocalDate.of(2018, 11, 1), vd1.end());
        assertEquals(31, vd1.difference(ChronoUnit.DAYS));
        assertEquals(0, vd1.difference(ChronoUnitX.QUARTERS));


        VagueDate vd2 = VagueDate.ofYear(2018);
        assertEquals(LocalDate.of(2018, 1, 1), vd2.begin());
        assertEquals(LocalDate.of(2019, 1, 1), vd2.end());
        assertEquals(365, vd2.difference(ChronoUnit.DAYS));

        VagueDate vd3 = VagueDate.of(
                LocalDate.of(2017, 12, 1),
                LocalDate.of(2018, 3, 1));
        assertEquals(LocalDate.of(2018, 3, 1), vd3.end());
        assertEquals(90, vd3.difference(ChronoUnit.DAYS));
    }

    @Test
    public void parse() throws Exception {

        assertEquals(365,VagueDate.parse("2017").difference(ChronoUnit.DAYS));
        assertEquals(365,VagueDate.parse("2017").difference(ChronoUnit.DAYS));
        assertEquals(LocalDate.parse("2017-07-01"),VagueDate.parse("2017Q3").begin());
        assertEquals(LocalDate.parse("2017-10-01"),VagueDate.parse("2017Q3").end());
        assertEquals(28,VagueDate.parse("2017-02").difference(ChronoUnit.DAYS));
    }


    @Test
    public void contains() throws Exception {

        VagueDate vd1 = VagueDate.of(2018, 10);
        assertTrue(vd1.contains(LocalDate.of(2018, 10, 1)));
        assertTrue(vd1.contains(LocalDate.of(2018, 10, 31)));
        assertFalse(vd1.contains(LocalDate.of(2018, 11, 1)));
        assertFalse(vd1.contains(LocalDate.of(2018, 9, 30)));
    }


    @Test
    public void average() throws Exception {
        VagueDate vd1 = VagueDate.of(2018, 10);
        assertEquals(LocalDate.of(2018, 10, 16), vd1.average());
    }

}