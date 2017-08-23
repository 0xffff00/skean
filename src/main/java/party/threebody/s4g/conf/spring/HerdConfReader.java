package party.threebody.s4g.conf.spring;


import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import party.threebody.skean.util.ObjectMappers;

public class HerdConfReader {
	@Autowired
	private ResourceLoader resourceLoader;
	
	
	public void s(){
		Resource r=resourceLoader.getResource("classpath:herd.conf.json");
		try {
			ObjectMappers.OM_SNAKE_CASE.reader().readValue(r.getFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
}
