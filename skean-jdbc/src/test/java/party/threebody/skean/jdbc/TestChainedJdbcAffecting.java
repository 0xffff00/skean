package party.threebody.skean.jdbc;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import party.threebody.skean.util.Maps;

import java.io.Serializable;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
@ActiveProfiles("memdb")
public class TestChainedJdbcAffecting {
	@Autowired
	private JdbcTemplate jdbcTmpl;
	@Autowired
	private ChainedJdbcTemplate q;

	@Test
	public void from_inserting() {
		assertEquals(1, q.from("t1ship").affect("code", "weig").val("CVN07", "67432").insert());
		assertEquals(1, q.from("t1ship").affect("code", "weig").val(Maps.of("code", "CVN08")).insert());
		assertEquals(1, q.from("t1ship").affect(Maps.of("code", "CVN09","name", "HATH")).insert());
		assertNotNull(q.from("t1ship").where("code like ?").val("CVN%").list());
	}

	@Test
	public void from_updating() {
		assertTrue(6 <= q.from("t1ship").affect("birth").where("1=1").val(1999).update());
		assertEquals(1, q.from("t1ship").affect("birth").by("code").val(1918, "CV06").update());;
		assertEquals(1, q.from("t1ship").affect("birth").by("code").val(Maps.of("birth", 1918,"code", "CV06")).update());
		assertEquals(1, q.from("t1ship").affect("weig").val(33001).by("code").val("CV06").update());
		assertEquals(33001,q.from("t1ship").by("code").val("CV06").single().get("weig"));

		assertEquals(1, q.from("t1ship").affect("weig").val(Maps.of("weig", 33002)).by("code").val("CV06").update());
		assertEquals(33002, q.from("t1ship").by("code").val("CV06").single().get("weig") );

		assertEquals(1, q.from("t1ship").affect("weig").val(33003).by(Maps.of("code", "CV06")).update());
		assertEquals(33003,q.from("t1ship").by("code").val("CV06").single().get("weig"));

		assertEquals(1, q.from("t1ship").affect(Maps.of("code", "CV06","name", null,"weig", 33599)).by(Maps.of("code", "CV06")).update());
		assertEquals(33599, q.from("t1ship").by("code").val("CV06").single().get("weig") );

		assertEquals(1,q.from("t1ship").affect(Maps.of("code", "CV06","name", "moha","weig", 33599)).by(Maps.of("code", "CV06","name", null)).update());
		assertEquals("moha",q.from("t1ship").by("code").val("CV06").single().get("name"));

		assertEquals( 0 , q.from("t1ship").affect(Maps.of("weig", 33599)).by(Maps.of("code", null)).update());

	}

	@Test
	public void from_deleting() {
		assertEquals(2, q.from("t1ship").by("birth").val(1987).delete());
		assertEquals(0, q.from("t1ship").by("birth").val(1987).count());
	}



}
