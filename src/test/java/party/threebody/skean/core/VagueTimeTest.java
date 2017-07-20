package party.threebody.skean.core;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.junit.Test;

import party.threebody.skean.core.VagueTime;

public class VagueTimeTest {

	@Test
	public void testOfInt() {
		VagueTime t = VagueTime.of(2017);
		assert t.begin().getYear() == 2017;
		assert t.end().equals(LocalDateTime.of(2018, 1, 1, 0, 0));
	}

	@Test
	public void testOfIntInt() {
		VagueTime t = VagueTime.of(2017, 12);
		assert t.end().getMonth().getValue() == 1;
	}

	@Test
	public void testOfIntIntInt() {
		VagueTime t = VagueTime.of(2017, 2, 28);
		assert t.end().getMonth().getValue() == 3;
	}

	@Test
	public void testOfIntIntIntIntIntInt() {
		VagueTime t = VagueTime.of(2017, 2, 28, 23, 59, 59);
		assert t.end().equals(LocalDateTime.of(2017, 3, 1, 0, 0));
	}

	@Test
	public void testParse(){
		VagueTime t=VagueTime.parse("2017Q3");
		assertEquals(LocalDateTime.parse("2017-07-01T00:00:00"),t.begin());
		assertEquals(LocalDateTime.parse("2017-10-01T00:00:00"),t.end());
	}

	@Test
	public void difference() {
		VagueTime t1 = VagueTime.of(2017, 2);
		assertEquals(28, t1.difference(ChronoUnit.DAYS));
		assertEquals(1, t1.difference(ChronoUnit.MONTHS));
		assertEquals(0, t1.difference(ChronoUnit.YEARS));
		VagueTime t2 = VagueTime.parse("2017Q4");
		assertEquals(92, t2.difference(ChronoUnit.DAYS));
		assertEquals(3, t2.difference(ChronoUnit.MONTHS));
		assertEquals(0, t2.difference(ChronoUnit.YEARS));
	}

	@Test
	public void testAverage() {
		VagueTime t = VagueTime.of(2017, 2);
		assertEquals(LocalDateTime.of(2017, 2, 15, 0, 0), t.average());
	}

	@Test
	public void testOfString() {
		assert VagueTime.parse("2013").end().getMonthValue() == 1;
		assert VagueTime.parse("2013Q4").end().getMonthValue() == 1;
		assert VagueTime.parse("2013-05-24").end().equals(LocalDateTime.of(2013, 5, 25, 0, 0));
		assert VagueTime.parse("2013-05-15 11:59:59").end().toLocalTime().equals(LocalTime.NOON);
	}

}
