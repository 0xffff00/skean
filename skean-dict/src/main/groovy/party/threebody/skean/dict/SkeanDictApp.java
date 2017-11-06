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

package party.threebody.skean.dict;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;
import party.threebody.skean.jdbc.ChainedJdbcTemplate;
import party.threebody.skean.jdbc.ChainedJdbcTemplateContext;
import party.threebody.skean.jdbc.SqlPrinter;
import party.threebody.skean.jdbc.SqlBuilder;
import party.threebody.skean.jdbc.SqlBuilderConfig;
import party.threebody.skean.jdbc.phrase.SqlBuilderMysqlImpl;

import javax.sql.DataSource;

@SpringBootApplication
public class SkeanDictApp {

    public static void main(String[] args) {
        SpringApplication.run(SkeanDictApp.class, args);
    }

    @Configuration
    class WebMvcConfig implements WebMvcConfigurer {

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
}

