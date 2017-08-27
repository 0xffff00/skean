package party.threebody.skean.dict.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.util.UrlPathHelper;

@Configuration
@EnableWebMvc
@Import({ DataConfig.class })
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Autowired Environment env;
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry r) {
		r.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		r.addResourceHandler("/p/**").addResourceLocations("file:///E:/ARC1/");
		r.addResourceHandler("/**").addResourceLocations("/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry r) {
		r.enableContentNegotiation(new MappingJackson2JsonView());
		r.jsp();

	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		/* 是否通过请求Url的扩展名来决定media type */
		configurer.favorPathExtension(true)
				/* 不检查Accept请求头 */
				.ignoreAcceptHeader(true).parameterName("mediaType")
				/* 设置默认的media type */
				.defaultContentType(MediaType.APPLICATION_JSON)
				/* 请求以.html结尾的会被当成MediaType.TEXT_HTML */
				.mediaType("html", MediaType.TEXT_HTML)
				/* 请求以.json结尾的会被当成MediaType.APPLICATION_JSON */
				.mediaType("json", MediaType.APPLICATION_JSON);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*")
				.exposedHeaders("X-Total-Count");
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		// enable matrix variable support
		if (configurer.getUrlPathHelper() == null) {
			UrlPathHelper urlPathHelper = new UrlPathHelper();
			urlPathHelper.setRemoveSemicolonContent(false);
			configurer.setUrlPathHelper(urlPathHelper);
		} else {
			configurer.getUrlPathHelper().setRemoveSemicolonContent(false);
		}
	}
}
