package party.threebody.skean.jdbc;

import org.springframework.jdbc.core.RowMapper;
import party.threebody.skean.core.SkeanException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @param <F0> class of field0
 * @param <F1> class of field1
 */
public class DualColsBeanRowMapper<F0, F1> implements RowMapper<DualColsBean<F0, F1>> {

    protected Class<F0> clazz0;
    protected Class<F1> clazz1;

    public DualColsBeanRowMapper(Class<F0> clazz0, Class<F1> clazz1) {
        this.clazz0 = clazz0;
        this.clazz1 = clazz1;
    }

    @Override
    public DualColsBean<F0, F1> mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DualColsBean<>(
                rs.getObject(1, clazz0),
                rs.getObject(2, clazz1)
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
//            if (type.equals(LocalDate.class)) {
//                java.sql.Date date = rs.getDate(columnIndex);
//                if (date == null) {
//                    return null;
//                }
//                return type.cast(date.toLocalDate());
//            }
//            if (type.equals(LocalDateTime.class)) {
//                java.sql.Timestamp timestamp = rs.getTimestamp(columnIndex);
//                if (timestamp == null) {
//                    return null;
//                }
//                return type.cast(timestamp.toLocalDateTime());
//            }
//            if (type.equals(LocalTime.class)) {
//                java.sql.Time time = rs.getTime(columnIndex);
//                if (time == null) {
//                    return null;
//                }
//                return type.cast(time.toLocalTime());
//            }
            return rs.getObject(columnIndex, type);
        } catch (SQLException e) {
            throw new SkeanException("cannot get column value from resultSet!", e);
        }
    }
}

