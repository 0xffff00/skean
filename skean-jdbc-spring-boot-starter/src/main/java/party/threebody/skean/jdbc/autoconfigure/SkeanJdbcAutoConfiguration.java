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

package party.threebody.skean.jdbc.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import party.threebody.skean.jdbc.ChainedJdbcTemplate;
import party.threebody.skean.jdbc.SqlBuilder;
import party.threebody.skean.jdbc.SqlPrinter;
import party.threebody.skean.jdbc.phrase.SqlBuilderMysqlImpl;

import javax.sql.DataSource;

/**
 * See Also:
 * https://github.com/snicoll-demos/spring-boot-master-auto-configuration/
 * https://fangjian0423.github.io/2016/11/16/springboot-custom-starter/
 */
@Configuration
@ConditionalOnClass({DataSource.class, JdbcTemplate.class})
@AutoConfigureAfter({DataSourceAutoConfiguration.class, JdbcTemplateAutoConfiguration.class})
@EnableConfigurationProperties({SqlBuilderConfigProperties.class, SqlPrinterConfigProperties.class})
public class SkeanJdbcAutoConfiguration {


    private final DataSource dataSource;

    private final JdbcTemplate jdbcTemplate;

    private final SqlBuilderConfigProperties sqlBuilderConfigProperties;

    private final SqlPrinterConfigProperties sqlPrinterConfigProperties;

    SkeanJdbcAutoConfiguration(DataSource dataSource, JdbcTemplate jdbcTemplate,
                               SqlBuilderConfigProperties sqlBuilderConfigProperties,
                               SqlPrinterConfigProperties sqlPrinterConfigProperties) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
        this.sqlBuilderConfigProperties = sqlBuilderConfigProperties;
        this.sqlPrinterConfigProperties = sqlPrinterConfigProperties;
    }

    @Autowired
    Environment env;

    @Bean
    @ConditionalOnMissingBean(ChainedJdbcTemplate.class)
    ChainedJdbcTemplate chainedJdbcTemplate() {
        SqlBuilder sqlBuilder = new SqlBuilderMysqlImpl(sqlBuilderConfigProperties);
        SqlPrinter sqlPrinter = new SqlPrinter(sqlPrinterConfigProperties);
        return new ChainedJdbcTemplate(dataSource, jdbcTemplate, sqlBuilder, sqlPrinter);

    }


}
