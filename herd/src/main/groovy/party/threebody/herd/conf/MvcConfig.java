package party.threebody.herd.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.util.UrlPathHelper;
import party.threebody.skean.lang.ObjectMappers;

import java.util.List;

@Configuration
@EnableWebMvc
@Import({DataConfig.class})
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    Environment env;

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
                .ignoreAcceptHeader(false)
                .parameterName("mediaType")
				/* 设置默认的media type */
                .defaultContentType(MediaType.APPLICATION_JSON)

				/* 请求以.html结尾的会被当成MediaType.TEXT_HTML */
                .mediaType("html", MediaType.TEXT_HTML)
                .mediaType("jpg", MediaType.IMAGE_JPEG)
				/* 请求以.json结尾的会被当成MediaType.APPLICATION_JSON */
                .mediaType("json", MediaType.APPLICATION_JSON);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new StringHttpMessageConverter());
        converters.add(new ResourceHttpMessageConverter());
        converters.add(new SourceHttpMessageConverter());
        converters.add(new FormHttpMessageConverter());
        final MappingJackson2HttpMessageConverter jack2hmc = new MappingJackson2HttpMessageConverter();
        jack2hmc.setObjectMapper(jacksonObjectMapper());
        converters.add(jack2hmc);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("X-Total-Count", "X-Total-Affected");
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

    @Bean
    ObjectMapper jacksonObjectMapper() {
        return ObjectMappers.DEFAULT;
    }

}
