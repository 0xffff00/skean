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

package party.threebody.skean.jdbc.rs;

import org.springframework.jdbc.core.RowMapper;
import party.threebody.skean.misc.SkeanException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @param <F0> class of field0
 * @param <F1> class of field1
 */
public class TripleColsBeanRowMapper<F0, F1, F2> implements RowMapper<TripleColsBean<F0, F1, F2>> {

    protected Class<F0> clazz0;
    protected Class<F1> clazz1;
    protected Class<F2> clazz2;

    public TripleColsBeanRowMapper(Class<F0> clazz0, Class<F1> clazz1, Class<F2> clazz2) {
        this.clazz0 = clazz0;
        this.clazz1 = clazz1;
        this.clazz2 = clazz2;
    }

    @Override
    public TripleColsBean<F0, F1, F2> mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TripleColsBean<>(
                rs.getObject(1, clazz0),
                rs.getObject(2, clazz1),
                rs.getObject(3, clazz2)
        );
    }

    /**
     * avoid bug: NPE on null LocalDate @ JDBC42ResultSet
     *
     * @param rs
     * @param type
     * @param <T>
     * @return
     */
    static <T> T getObject(ResultSet rs, int columnIndex, Class<T> type) {
        try {
            return rs.getObject(columnIndex, type);
        } catch (SQLException e) {
            throw new SkeanException("cannot get column value from resultSet!", e);
        }
    }
}

