/*
 * Copyright 2017-present  Skean Project Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package party.threebody.skean.web.autoconfigure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;
import party.threebody.skean.web.data.CriteriaBuilder;
import party.threebody.skean.web.data.OptAsTailCriteriaBuilder;
import party.threebody.skean.web.data.SkeanWebConfig;

/**
 *
 */
@Configuration
@EnableConfigurationProperties(SkeanWebConfigProperties.class)
public class SkeanWebAutoConfiguration {

    private final SkeanWebConfigProperties skeanWebConfigProperties;
    private Environment env;

    public SkeanWebAutoConfiguration(SkeanWebConfigProperties skeanWebConfigProperties) {
        this.skeanWebConfigProperties = skeanWebConfigProperties;
    }

    @Bean
    public SkeanWebConfig skeanWebConfig() {
        return skeanWebConfigProperties;
    }

    @Bean
    public CriteriaBuilder criteriaBuilder() {
        return new OptAsTailCriteriaBuilder(skeanWebConfigProperties);

    }


    @Configuration
    @EnableWebMvc
    public class WebMvcConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods("*")
                    .allowedHeaders("*")
                    .exposedHeaders("X-Total-Count", "X-Total-Affected");   //TODO make configuable
        }

        @Override
        public void configurePathMatch(PathMatchConfigurer configurer) {
            // enable matrix variables support
            final UrlPathHelper urlPathHelper = new UrlPathHelper();
            urlPathHelper.setRemoveSemicolonContent(false);
            configurer.setUrlPathHelper(urlPathHelper);
        }


    }


}
