package party.threebody.s4g.conf.spring;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{RootConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}  
  

}  