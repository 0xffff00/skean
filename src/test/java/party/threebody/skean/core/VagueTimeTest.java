package party.threebody.skean.core;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Test;

import party.threebody.skean.core.VagueTime;

public class VagueTimeTest {

	@Test
	public void testOfInt() {
		VagueTime t=VagueTime.of(2017);
		assert t.getBegin().getYear()==2017;
		assert t.getEnd().equals(LocalDateTime.of(2018, 1, 1, 0, 0));
	}

	@Test
	public void testOfIntInt() {
		VagueTime t=VagueTime.of(2017,12);
		assert t.getEnd().getMonth().getValue()==1;
	}

	@Test
	public void testOfIntIntInt() {
		VagueTime t=VagueTime.of(2017,2,28);
		assert t.getEnd().getMonth().getValue()==3;
	}

	@Test
	public void testOfIntIntIntIntIntInt() {
		VagueTime t=VagueTime.of(2017,2,28,23,59,59);
		assert t.getEnd().equals(LocalDateTime.of(2017, 3, 1, 0, 0));
	}

	@Test
	public void testOfString() {
		assert VagueTime.of("2013").getEnd().getMonthValue()==1;
		assert VagueTime.of("2013Q4").getEnd().getMonthValue()==1;
		assert VagueTime.of("2013-05-24").getEnd().equals(LocalDateTime.of(2013, 5, 25, 0, 0));
		assert VagueTime.of("2013-05-15 11:59:59").getEnd().toLocalTime().equals(LocalTime.NOON);
	}


}
