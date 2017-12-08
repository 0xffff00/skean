package party.threebody.skean.core.util;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.junit.Test;
import party.threebody.tmp.useless.VagueDateTime;

public class VagueDateTimeTest {
	@Test
	public void testOfLong() {
		VagueDateTime.of(20170722l);
		VagueDateTime.of(20170722000000l);
		/*
		20170791	//2017-07~08
		20170722	//2017-07-22
		20170792	//2017-07~09(2017Q3)
		20172430	//2017W43
		20009000	//2000~2000
		20000000	//2000~2000
		20009099	//2000~2099(21st centry)
		20109009	//2010~2019
		20199004	//2019~2023		
		yyyy9xxx	//yyyy~yyyy+xxx
		yyyy2wwx	//yyyy-ww[+x]
		yyyymmdd
		yyyymm9x	//yyyy-MM[+x]
		yyyy9001	//yyyy[+1]
		yyyy	
		
*/
	}
	@Test
	public void testOfYear() {
		VagueDateTime t = VagueDateTime.ofYear(2017);
		assert t.begin().getYear() == 2017;
		assert t.end().equals(LocalDateTime.of(2018, 1, 1, 0, 0));
	}

	@Test
	public void testOfIntInt() {
		VagueDateTime t = VagueDateTime.of(2017, 12);
		assert t.end().getMonth().getValue() == 1;
	}

	@Test
	public void testOfIntIntInt() {
		VagueDateTime t = VagueDateTime.of(2017, 2, 28);
		assert t.end().getMonth().getValue() == 3;
	}

	@Test
	public void testOfIntIntIntIntIntInt() {
		VagueDateTime t = VagueDateTime.of(2017, 2, 28, 23, 59, 59);
		assert t.end().equals(LocalDateTime.of(2017, 3, 1, 0, 0));
	}

	@Test
	public void testParse(){
		VagueDateTime t=VagueDateTime.parse("2017Q3");
		assertEquals(LocalDateTime.parse("2017-07-01T00:00:00"),t.begin());
		assertEquals(LocalDateTime.parse("2017-10-01T00:00:00"),t.end());
	}

	@Test
	public void difference() {
		VagueDateTime t1 = VagueDateTime.of(2017, 2);
		assertEquals(28, t1.difference(ChronoUnit.DAYS));
		assertEquals(1, t1.difference(ChronoUnit.MONTHS));
		assertEquals(0, t1.difference(ChronoUnit.YEARS));
		VagueDateTime t2 = VagueDateTime.parse("2017Q4");
		assertEquals(92, t2.difference(ChronoUnit.DAYS));
		assertEquals(3, t2.difference(ChronoUnit.MONTHS));
		assertEquals(0, t2.difference(ChronoUnit.YEARS));
	}

	@Test
	public void testAverage() {
		VagueDateTime t = VagueDateTime.of(2017, 2);
		assertEquals(LocalDateTime.of(2017, 2, 15, 0, 0), t.average());
	}

	@Test
	public void testOfString() {
		assert VagueDateTime.parse("2013").end().getMonthValue() == 1;
		assert VagueDateTime.parse("2013Q4").end().getMonthValue() == 1;
		assert VagueDateTime.parse("2013-05-24").end().equals(LocalDateTime.of(2013, 5, 25, 0, 0));
		assert VagueDateTime.parse("2013-05-15 11:59:59").end().toLocalTime().equals(LocalTime.NOON);
	}

}
