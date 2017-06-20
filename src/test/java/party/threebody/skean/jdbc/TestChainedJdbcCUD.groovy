package party.threebody.skean.jdbc;

import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration

import party.threebody.s4g.conf.spring.RootConfig
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = RootConfig.class)
@ActiveProfiles("memdb")
@Rollback
class TestChainedJdbcCUD {
	@Autowired
	JdbcTemplate jdbcTmpl;
	@Autowired
	ChainedJdbcTemplate q;
	
	@Test
	public void from_inserting(){
		assert 1== q.from("t1ship").affect("code","weig").val("CVN07","67432").insert()
		assert 1== q.from("t1ship").affect("code","weig").val([code:"CVN08"]).insert()
		assert 1== q.from("t1ship").val([code:"CVN09",name:"HATH"]).insert()
		
		assertNotNull q.from("t1ship").where("code like ?").val('CVN%').list()
	}
	@Test
	public void from_updating(){
		assert 6<q.from("t1ship").affect("birth").where("1=1").val(1999).update();
		assert 1==q.from("t1ship").affect("birth").by("code").val(1918,"CV06").update();
		assert 1==q.from("t1ship").affect("birth").by("code").val([birth:1918,code:"CV06"]).update();
		
		assert 1==q.from('t1ship').affect('weig').val(33001).by('code').val('CV06').update()
		assert q.from('t1ship').by('code').val('CV06').single()['weig']==33001
		
		assert 1==q.from('t1ship').affect('weig').val([weig:33002]).by('code').val('CV06').update()
		assert q.from('t1ship').by('code').val('CV06').single()['weig']==33002
		
		assert 1==q.from('t1ship').affect('weig').val(33003).by([code:'CV06']).update()
		assert q.from('t1ship').by('code').val('CV06').single()['weig']==33003
		
		assert 1==q.from('t1ship').affect([code:'CV06',name:null,weig:33599]).by([code:'CV06']).update()
		assert q.from('t1ship').by('code').val('CV06').single()['weig']==33599
		
		assert 1==q.from('t1ship').affect([code:'CV06',name:'moha',weig:33599]).by([code:'CV06',name:null]).update()
		assert q.from('t1ship').by('code').val('CV06').single()['name']=='moha'
		assert 0==q.from('t1ship').affect([weig:33599]).by([code:null]).update()
		
	}
	
	
	@Test
	public void from_deleting(){
		assert 2==q.from("t1ship").by("birth").val(1987).delete();
		assert q.from("t1ship").by("birth").val(1987).count()==0;
	}

}

