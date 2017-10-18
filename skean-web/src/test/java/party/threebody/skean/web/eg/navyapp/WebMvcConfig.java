package party.threebody.skean.web.eg.navyapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    Environment env;


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
        // enable matrix variables support
        final UrlPathHelper urlPathHelper = new UrlPathHelper();
        configurer.setUrlPathHelper(urlPathHelper);
        configurer.getUrlPathHelper().setRemoveSemicolonContent(false);
    }


}