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

package party.threebody.skean.jdbc;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import party.threebody.skean.jdbc.util.SqlAndArgs;
import party.threebody.skean.jdbc.util.SqlPrintUtils;
import party.threebody.skean.lang.DateTimeFormatters;

import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.List;

public class SqlPrinter {

    SqlPrinterConfig config;
    static final Logger logger = LoggerFactory.getLogger(SqlPrinter.class);

    public SqlPrinter(SqlPrinterConfig config) {
        super();
        this.config = config;
    }

    public void printSql(String sql, Object[] args) {
        if (!config.isPrintSqlAndResult()) {
            return;
        }
        logger.info(">>>>>>>>>>>   SQL  >>>>>>>>>>>\n{}", SqlPrintUtils.ansiFormatSql(sql, args));
    }

    public void printSql(SqlAndArgs sa) {
        if (!config.isPrintSqlAndResult()) {
            return;
        }
        logger.info(">>>>>>>>>>>   SQL  >>>>>>>>>>>\n{}", sa.toAnsiString());
    }

    public void printRowNumAffected(int rowNumAffected) {
        if (!config.isPrintSqlAndResult()) {
            return;
        }
        logger.info("<<<<<<<<<<< {} rows(s) affected. <<<<<<<<<<<", rowNumAffected);
    }

    public <T> void printResultBean(T resultBean) {
        printResultList(Arrays.asList(resultBean));
    }

    public <T> void printResultList(List<T> resultList) {
        if (!config.isPrintSqlAndResult()) {
            return;
        }
        int count = resultList.size();
        String text = null;
        if (count == 0) {
            text = "(None)";
        } else {
            int rsmax = config.getMaxCharsToPrintSqlResult();
            int lnmax = config.getMaxCharsToPrintInOneLine() - 1;
            StringBuilder sb = new StringBuilder();
            int i = 0, ln = 0;
            for (; i < count; i++) {
                String str = stringify(resultList.get(i));
                if (str == null) {
                    str = "(null)";
                }
                if (sb.length() + str.length() > rsmax) {
                    break;
                }
                ln += str.length();
                sb.append(str);
                if (ln > lnmax) {
                    sb.append("\n");
                    ln = 0;
                } else {
                    sb.append("\t");
                    ln += 4;
                }
            }
            if (i < count) {
                sb.append("...(other ").append(count - i).append(" items left)");
            }
            text = sb.toString();
        }
        text = SqlPrintUtils.ansiFormat(text, SqlPrintUtils.ANSI.CYAN);
        logger.info("<<<<<<<<<<< {} row(s) returned: <<<<<<<<<<<\n{}", count, text);

    }

    private <T> String stringify(T bean) {
        if (bean == null) {
            return null;
        }
        switch (config.getPrintSqlResultStrategy()) {
            case JACKSON:
                // TODO refactor sqlPrintConfig
            case REFLECTION:
                if (bean.toString().startsWith(bean.getClass().getName() + "@")) {
                    return ToStringBuilder.reflectionToString(bean);
                } else {
                    return bean.toString();
                }
            case ALWAYS_TOSTRING:
                if (bean instanceof TemporalAccessor) {
                    return DateTimeFormatters.DEFAULT.format((TemporalAccessor) bean);
                }
            default:
                return bean.toString();

        }
    }
}
